package uicontrollers;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import businessLogic.BlFacade;
import domain.Event;
import domain.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ui.MainGUI;
import utils.Dates;

public class setFeesController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label answerLbl;

    @FXML
    private Button closeBtn;

    @FXML
    private DatePicker dateSelector;

    @FXML
    private TableColumn<Event, String> eventTxt;

    @FXML
    private TableColumn<Event, Integer> eventId;

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TextField feeField;

    @FXML
    private TableColumn<Question, Integer> questionId;

    @FXML
    private TableView<Question> questionTable;

    @FXML
    private TableColumn<Question, String> questionTxt;

    @FXML
    private TextField resultField;

    @FXML
    private Button setFeeBtn;

    private BlFacade businessLogic;

    private MainGUI mainGUI;

    private ObservableList questionData;

    private ObservableList eventData;

    private List<LocalDate> holidays = new ArrayList<>();

    public setFeesController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void closeClick(ActionEvent event) {
        mainGUI.showMain();
    }

    @FXML
    void setFeeClick(ActionEvent event) {

        answerLbl.getStyleClass().clear();
        try{
            if(dateSelector.getValue() == null){
                answerLbl.setText("You must select a date");
                answerLbl.getStyleClass().setAll("lbl","lbl-danger");
            }
            else if(eventTable.getSelectionModel().getSelectedItem() == null){
                answerLbl.setText("You must select an event");
                answerLbl.getStyleClass().setAll("lbl","lbl-danger");
            }
            else if(questionTable.getSelectionModel().getSelectedItem() == null){
                answerLbl.setText("You must select a question");
                answerLbl.getStyleClass().setAll("lbl","lbl-danger");
            }
            else if(resultField.getText().equals("")){
                answerLbl.setText("You must enter a result");
                answerLbl.getStyleClass().setAll("lbl","lbl-danger");
            }
            else if(feeField.getText().equals("")){
                answerLbl.setText("You must select a fee");
                answerLbl.getStyleClass().setAll("lbl","lbl-danger");
            }
            else if(Float.parseFloat(feeField.getText()) < 1){
                answerLbl.setText("The fee must be at least 1");
                answerLbl.getStyleClass().setAll("lbl","lbl-danger");
            }
            //else if(businessLogic.feeExists(answerLbl.getText(),(String) questionTableModel.getValueAt(j,1))) {
            //    answerLbl.setText("You must select another result.");
            //}
            else{
                Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
                String question = questionTable.getSelectionModel().getSelectedItem().getQuestion();
                businessLogic.setFee(resultField.getText(), Float.parseFloat(feeField.getText())
                        , question, selectedEvent);
                answerLbl.getStyleClass().clear();
                answerLbl.getStyleClass().setAll("lbl", "lbl-success");
                answerLbl.setText("Fee correctly added");
            }
        }
        catch (NumberFormatException ex){
            answerLbl.setText("The fee must be a number");
            answerLbl.getStyleClass().setAll("lbl","lbl-danger");
        }

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


        // get a reference to datepicker inner content
        // attach a listener to the  << and >> buttons
        // mark events for the (prev, current, next) month and year shown
        dateSelector.setOnMouseClicked(e -> {
            DatePickerSkin skin = (DatePickerSkin) dateSelector.getSkin();
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

        dateSelector.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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

        dateSelector.setOnAction(actionEvent -> {
            eventTable.getItems().clear();
            questionTable.getItems().clear();
            resultField.setText("");
            feeField.setText("");
            answerLbl.setText("");
            answerLbl.getStyleClass().clear();
            Vector<Event> events= businessLogic.getEvents(Dates.convertToDate(dateSelector.getValue()));
            for(Event e:events){
                eventTable.getItems().add(e);
            }
        });

        eventTable.getSelectionModel().selectedItemProperty().addListener((obs, oldselection, newselection) -> {
            if(newselection != null){
                questionTable.getItems().clear();
                Vector<Question> questions = eventTable.getSelectionModel().getSelectedItem().getQuestions();
                for(Question q:questions){
                    questionTable.getItems().add(q);
                }
            }
        });
        questionId.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        questionTxt.setCellValueFactory(new PropertyValueFactory<>("question"));

        eventId.setCellValueFactory(new PropertyValueFactory<>("eventNumber"));
        eventTxt.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

}




