package ehu.uicontrollers;

import ehu.businessLogic.BlFacade;
import ehu.utils.Dates;
import ehu.exceptions.EventAlreadyExists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ehu.ui.MainGUI;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CreateEventsController implements Controller {

    @FXML
    private Label answerLbl;

    @FXML
    private Button backBtn;

    @FXML
    private Button createBtn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField eventField;

    @FXML
    private ResourceBundle resources;

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    private List<LocalDate> holidays = new ArrayList<>();

    public CreateEventsController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void backClick(ActionEvent event) {
        datePicker.getEditor().clear();
        eventField.clear();
        answerLbl.setText("");
        answerLbl.getStyleClass().clear();
        mainGUI.showMain();
    }

    @FXML
    void createClick(ActionEvent event) throws EventAlreadyExists {
        if(datePicker.getValue() == null){
            answerLbl.setText(resources.getString("selectDate"));
            answerLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(eventField.getText().equals("")){
            answerLbl.setText(resources.getString("enterEvent"));
            answerLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else{
            try{
                businessLogic.createEvent(eventField.getText(),Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                answerLbl.setText(resources.getString("eventCreated"));
                answerLbl.getStyleClass().setAll("lbl","lbl-success");
            }
            catch (Exception e){
                answerLbl.setText(resources.getString("eventExists"));
                answerLbl.getStyleClass().setAll("lbl","lbl-danger");
            }
            eventField.setText("");
        }
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


    @FXML
    void initialize() {

        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());

        datePicker.setOnMouseClicked(e -> {
            // get a reference to datepicker inner content
            // attach a listener to the  << and >> buttons
            // mark events for the (prev, current, next) month and year shown
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
    }

    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }

    private void setEvents(int year, int month) {
        Date date = Dates.toDate(year,month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            holidays.add(Dates.convertToLocalDateViaInstant(day));
        }
    }

}
