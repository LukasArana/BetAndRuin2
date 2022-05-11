package ehu.uicontrollers;

import ehu.businessLogic.BlFacade;
import ehu.domain.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ehu.ui.MainGUI;
import ehu.utils.Dates;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class RemoveEventController implements Controller {

    @FXML
    private Button btnClose;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TableColumn<Event, Integer> ec1;

    @FXML
    private TableColumn<Event, String> ec2;

    @FXML
    private TableView<Event> tblEvents;

    @FXML
    private Button removeBt;

    @FXML
    private Label messageLbl;

    private MainGUI mainGUI;
    private BlFacade businessLogic;
    private List<LocalDate> holidays = new ArrayList<>();




    public RemoveEventController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void closeClick(ActionEvent event) {
        tblEvents.getItems().clear();
        datepicker.getEditor().clear();
        mainGUI.showMain();
    }

    @FXML
    void removeClick(ActionEvent event) {
        messageLbl.setText("You must choose an event");
        messageLbl.getStyleClass().setAll("lbl","lbl-danger");
    }

    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }

    private void setEvents(int year, int month) {

        Date date = Dates.toDate(year, month);

        for (Date day : businessLogic.getEventsMonth(date)) {
            holidays.add(Dates.convertToLocalDateViaInstant(day));
        }
    }

    @FXML
    void initialize() {

        removeBt.setDisable(true);

        // only show the text of the event in the combobox (without the id)
        Callback<ListView<Event>, ListCell<Event>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getDescription());
            }
        };





        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());


        // get a reference to datepicker inner content
        // attach a listener to the  << and >> buttons
        // mark events for the (prev, current, next) month and year shown
        datepicker.setOnMouseClicked(e -> {
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

        // when a date is selected...
        datepicker.setOnAction(actionEvent -> {
            removeBt.setDisable(false);
            tblEvents.getItems().clear();

            Vector<Event> events = businessLogic.getEvents(Dates.convertToDate(datepicker.getValue()));
            for (Event ev : events) {
                tblEvents.getItems().add(ev);
            }

            // Bind columns to Event attributes
            ec1.setCellValueFactory(new PropertyValueFactory<>("eventNumber"));
            ec2.setCellValueFactory(new PropertyValueFactory<>("description"));
        });

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

}