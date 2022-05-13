package ehu.uicontrollers;

import ehu.businessLogic.BlFacade;
import ehu.domain.Bet;
import ehu.domain.Event;
import ehu.domain.Fee;
import ehu.domain.Question;
import ehu.utils.Dates;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import ehu.businessLogic.*;
import ehu.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.*;
import ehu.ui.MainGUI;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ehu.ui.MainGUI;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class RemoveBetController implements Controller {

    @FXML
    private Button backBtn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Event, String> eventColumn;

    @FXML
    private TableColumn<Event, Integer> eventIdColumn;

    @FXML
    private TableView<Event> tblEvents;

    @FXML
    private Button publishBtn;

    @FXML
    private TableColumn<Question, Integer> questionColumn;

    @FXML
    private TableColumn<Question, String> questionIdColumn;

    @FXML
    private TableView<Question> tblQuestions;

    @FXML
    private Label messageLbl;

    @FXML
    private ComboBox<Bet> resultCombo;

    @FXML
    private ResourceBundle resources;

    @FXML
    private ComboBox<Fee> feeComboBox;

    private MainGUI mainGUI;
    private BlFacade businessLogic;
    private List<LocalDate> holidays = new ArrayList<>();
    private ObservableList<Bet> bets;
    private ObservableList<Fee> fees;


    public RemoveBetController(BlFacade businessLogic) {
        this.businessLogic = businessLogic;
    }

    @FXML
    void goBack(ActionEvent event) {
        tblEvents.getItems().clear();
        tblQuestions.getItems().clear();
        datePicker.getEditor().clear();
        resultCombo.getItems().clear();
        messageLbl.setText("");
        messageLbl.getStyleClass().clear();
        mainGUI.showMain();
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    void publish(ActionEvent event) {
        messageLbl.getStyleClass().clear();

        if(datePicker.getValue().isBefore(LocalDate.now())){
            messageLbl.setText(resources.getString("eventFinished"));
            messageLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(tblEvents.getSelectionModel().getSelectedItem() == null){
            messageLbl.setText(resources.getString("selectEvent"));
            messageLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(tblQuestions.getSelectionModel().getSelectedItem() == null){
            messageLbl.setText(resources.getString("selectQuestion"));
            messageLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(resultCombo.getSelectionModel().getSelectedItem() == null){
            messageLbl.setText(resources.getString("chooseResult"));
            messageLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else{
            businessLogic.removeBet(resultCombo.getSelectionModel().getSelectedItem(),mainGUI.getUsername());
            resultCombo.getItems().remove(resultCombo.getSelectionModel().getSelectedItem());
            messageLbl.setText(resources.getString("publishedSuccess"));
            messageLbl.getStyleClass().setAll("lbl","lbl-success");
        }
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI=mainGUI;
    }

    private void setEvents(int year, int month) {

        Date date = Dates.toDate(year, month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            holidays.add(Dates.convertToLocalDateViaInstant(day));
        }
    }

    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }

    @FXML
    void initialize() {

        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());
        datePicker.setValue(LocalDate.now());

        // get a reference to datepicker inner content
        // attach a listener to the  << and >> buttons
        // mark events for the (prev, current, next) month and year shown
        datePicker.setOnMouseClicked(e -> {
            DatePickerSkin skin = (DatePickerSkin) datePicker.getSkin();
            skin.getPopupContent().lookupAll(".button").forEach(node -> {
                node.setOnMouseClicked(event -> {
                    List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();
                    String month = ((Label) (labels.get(0))).getText();
                    String year = ((Label) (labels.get(1))).getText();
                    YearMonth ym = Dates.getYearMonth(month + " " + year);
                    setEventsPrePost(ym.getYear(), ym.getMonthValue());
                });
            });


        });

        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (holidays.contains(item)) {
                                this.setStyle("-fx-background-color: pink");
                            }
                        }
                    }
                };
            }
        });

        datePicker.setOnAction(actionEvent -> {
            tblEvents.getItems().clear();
            tblQuestions.getItems().clear();
            resultCombo.getItems().clear();
            messageLbl.setText("");
            messageLbl.getStyleClass().clear();
            Vector<Event> events= businessLogic.getEvents(Dates.convertToDate(datePicker.getValue()));
            for(Event e:events){
                tblEvents.getItems().add(e);
            }
        });

        tblEvents.getSelectionModel().selectedItemProperty().addListener((obs, oldselection, newselection) -> {
            if(newselection != null){
                tblQuestions.getItems().clear();
                Vector<Question> questions = businessLogic.getQuestions(tblEvents.getSelectionModel().getSelectedItem());
                for(Question q:questions){
                    if(q.getResult() == null){
                        tblQuestions.getItems().add(q);
                    }
                }
            }
        });
        questionIdColumn.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));

        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventNumber"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        tblQuestions.getSelectionModel().selectedItemProperty().addListener((obs, oldselection, newselection) -> {
            if(newselection != null){
                feeComboBox.getItems().clear();
                fees = FXCollections.observableArrayList(new ArrayList<>());
                fees.setAll(tblQuestions.getSelectionModel().getSelectedItem().getFeeList());
                //Fees = tblQuestions.getSelectionModel().getSelectedItem().getFeeList();
                feeComboBox.setItems(fees);
                if(feeComboBox.getItems().size() == 0){
                    publishBtn.setDisable(true);
                    messageLbl.setText(resources.getString("noFees"));
                    messageLbl.getStyleClass().setAll("lbl", "lbl-danger");
                }else {
                    //betBt.setDisable(false);
                    messageLbl.setText("");
                    messageLbl.getStyleClass().clear();

                }
            }
        });


        feeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldselection, newselection) -> {
            if(newselection != null){
                publishBtn.setDisable(false);
                resultCombo.getItems().clear();
                bets = FXCollections.observableArrayList(new ArrayList<>());
                    bets.setAll(businessLogic.getBetsForQuestion(tblQuestions.getSelectionModel().getSelectedItem(), businessLogic.getCurrentUser(), feeComboBox.getSelectionModel().getSelectedItem()));
                    //Fees = tblQuestions.getSelectionModel().getSelectedItem().getFeeList();
                    resultCombo.setItems(bets);
                if(resultCombo.getItems().size() == 0){
                    publishBtn.setDisable(true);
                    messageLbl.setText(resources.getString("noBets"));
                    messageLbl.getStyleClass().setAll("lbl", "lbl-danger");
                }else {
                    //betBt.setDisable(false);
                    messageLbl.setText("");
                    messageLbl.getStyleClass().clear();
                }
            }
        });


    }

}
