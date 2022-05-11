package ehu.uicontrollers;

import ehu.businessLogic.BlFacade;
import ehu.domain.Event;
import ehu.domain.Question;
import ehu.domain.fee;
import ehu.utils.Dates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ehu.ui.MainGUI;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class PlaceBetController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button betBt;

    @FXML
    private Button btnClose;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TableColumn<Event, Integer> ec1;

    @FXML
    private TableColumn<Event, String> ec2;

    @FXML
    private ComboBox<fee> feeComboBox;

    @FXML
    private TableColumn<Question, Integer> qc1;

    @FXML
    private TableColumn<Question, String> qc2;

    @FXML
    private TextField stakeField;

    @FXML
    private TableView<Event> tblEvents;

    @FXML
    private TableView<Question> tblQuestions;

    @FXML
    private Label winningsLb;

    @FXML
    private Label messageLbl;

    @FXML
    private Label outputLbl;

    @FXML
    private Label balanceLbl;

    private MainGUI mainGUI;
    private BlFacade businessLogic;
    private List<LocalDate> holidays = new ArrayList<>();
    private ObservableList<fee> fees;

    public PlaceBetController(BlFacade bl) {
        businessLogic = bl;
    }


    @FXML
    void closeClick(ActionEvent event) {
        mainGUI.showMain();
        feeComboBox.getItems().clear();
        tblEvents.getItems().clear();
        tblQuestions.getItems().clear();
        stakeField.clear();
        messageLbl.setText("");
        messageLbl.getStyleClass().clear();
        balanceLbl.getStyleClass().clear();
        balanceLbl.setText("");
        outputLbl.getStyleClass().clear();
        outputLbl.setText("");
    }

    @FXML
    void placeBetClick(ActionEvent event) {
        fee f = feeComboBox.getSelectionModel().getSelectedItem();
        Event ev = tblEvents.getSelectionModel().getSelectedItem();
        Question q = tblQuestions.getSelectionModel().getSelectedItem();
        messageLbl.getStyleClass().clear();
        try {
            if (stakeField.getText().equals("")) {
                messageLbl.setText(resources.getString("introduceStake"));
                messageLbl.getStyleClass().setAll("lbl", "lbl-danger");
            }else if(Float.parseFloat(stakeField.getText()) < 1){
                messageLbl.setText(resources.getString("minbet1"));
                messageLbl.getStyleClass().setAll("lbl", "lbl-danger");
            }else if (Float.parseFloat(stakeField.getText()) > businessLogic.getCurrency(mainGUI.getUsername())) {
                messageLbl.setText(resources.getString("notEnoughMoney"));
                messageLbl.getStyleClass().setAll("lbl", "lbl-danger");
            }else {
                messageLbl.setText(resources.getString("betPlaced"));
                messageLbl.getStyleClass().setAll("lbl", "lbl-success");
                outputLbl.setText(resources.getString("Fee") + ": "+feeComboBox.getSelectionModel().getSelectedItem()
                        +"\n" + resources.getString("Stake") + ": "+stakeField.getText()+"€"+
                        "\n" + resources.getString("Winnings") + ": "+ Float.parseFloat(stakeField.getText())*feeComboBox.getSelectionModel().getSelectedItem().getFee()+"€");
                businessLogic.placeBet(Float.parseFloat(stakeField.getText()), mainGUI.getUsername(),f,q,ev);
                balanceLbl.setText(businessLogic.getCurrency(mainGUI.getUsername()).toString()+"€");
                stakeField.setText("");
                winningsLb.setText("0.0");
                feeComboBox.getSelectionModel().select(null);
            }
        }
        catch (NumberFormatException e) {
            messageLbl.setText(resources.getString("stakeNumber"));
            messageLbl.getStyleClass().setAll("lbl", "lbl-danger");
        }
    }

    @FXML
    void enteringStake(ActionEvent event) {
        Float stake = Float.parseFloat(stakeField.getText());
        Float fee = feeComboBox.getSelectionModel().getSelectedItem().getFee();
        winningsLb.setText(String.valueOf(stake*fee)+"€");
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
    void initialize(){
        betBt.setDisable(true);
        datepicker.setOnMouseClicked(e -> {
            balanceLbl.setText(businessLogic.getCurrency(mainGUI.getUsername()).toString()+"€");
            DatePickerSkin skin = (DatePickerSkin) datepicker.getSkin();
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

        datepicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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

        datepicker.setOnAction(actionEvent -> {
            tblEvents.getItems().clear();
            tblQuestions.getItems().clear();
            messageLbl.setText("");
            messageLbl.getStyleClass().clear();
            Vector<Event> events= businessLogic.getEvents(Dates.convertToDate(datepicker.getValue()));
            for(Event e:events){
                tblEvents.getItems().add(e);
            }
        });

        tblEvents.getSelectionModel().selectedItemProperty().addListener((obs, oldselection, newselection) -> {
            if(newselection != null){
                tblQuestions.getItems().clear();
                Vector<Question> questions = tblEvents.getSelectionModel().getSelectedItem().getQuestions();
                for(Question q:questions){
                    tblQuestions.getItems().add(q);
                }
            }
        });

        qc1.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        qc2.setCellValueFactory(new PropertyValueFactory<>("question"));

        ec1.setCellValueFactory(new PropertyValueFactory<>("eventNumber"));
        ec2.setCellValueFactory(new PropertyValueFactory<>("description"));

        tblQuestions.getSelectionModel().selectedItemProperty().addListener((obs, oldselection, newselection) -> {
            if(newselection != null){
                feeComboBox.getItems().clear();
                fees = FXCollections.observableArrayList(new ArrayList<>());
                fees.setAll(tblQuestions.getSelectionModel().getSelectedItem().getFeeList());
                //fees = tblQuestions.getSelectionModel().getSelectedItem().getFeeList();
                feeComboBox.setItems(fees);
                if(feeComboBox.getItems().size() == 0){
                    betBt.setDisable(true);
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
                betBt.setDisable(false);
                if(!stakeField.getText().equals("")){
                    try {
                        Float stake = Float.parseFloat(stakeField.getText());
                        Float fee = newselection.getFee();
                        winningsLb.setText(String.valueOf(stake*fee)+"€");
                    }
                    catch (NumberFormatException e){
                        messageLbl.setText(resources.getString("stakeNumber"));
                        messageLbl.getStyleClass().setAll("lbl", "lbl-danger");
                        stakeField.setText("");
                    }
                }
            }
        });



    }
}
