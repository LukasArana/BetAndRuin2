package ehu.uicontrollers;

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
import javafx.scene.control.*;
import ehu.ui.MainGUI;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class RemoveBetController implements Controller{

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    ObservableList<Event> eventData;
    ObservableList<Bet> betData;
    ObservableList<Question> questionData;


    private DatePicker datePicker;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<Bet, Float> currencyColumn;

    @FXML
    private Label currentBalanceLbl;

    @FXML
    private Label errorLbl;

    @FXML
    private TableColumn<Event, String> eventColumn;

    @FXML
    private TableColumn<Event, Integer> idColumn;

    @FXML
    private Label moneyLbl;

    @FXML
    private TableColumn<Question, String> questionColumn;

    @FXML
    private TableColumn<Question, Integer> questionColumnID;

    @FXML
    private TableView<Question> questionTable;

    @FXML
    private Button removeBtn;

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Bet, String> resultColumn;

    @FXML
    private TableView<Bet> resultTable;


    @FXML
    void goBack(ActionEvent event) {
        mainGUI.showMain();
        eventTable.getItems().clear();
        questionTable.getItems().clear();
        resultTable.getItems().clear();
    }

    @FXML
    void removeBet(ActionEvent event) {
/*
        User actual = businessLogic.getCurrentUser();
        Bet selected = removeTable.getSelectionModel().getSelectedItem();

        if (selected == null){
            errorLbl.getStyleClass().setAll("lbl", "lbl-danger");
            errorLbl.setText("You must select a bet to delete");
        }else {
            Float actualBalance = businessLogic.getCurrency(actual.getUsername()) - selected.getBalance();
            moneyLbl.setText(String.valueOf(actualBalance));
            businessLogic.updateCurrency(actualBalance - actual.getAvailableMoney(), actual.getUsername());
            removeTable.getItems().remove(selected);
            int i = removeTable.getSelectionModel().getSelectedIndex();
            actual.getMovements().remove(i);
            businessLogic.removeBet(selected);
            errorLbl.setText("Bet removed");
            errorLbl.getStyleClass().setAll("lbl", "lbl-success");

        }*/
    }


    public RemoveBetController(BlFacade businessLogic){this.businessLogic =  businessLogic;}

    @Override
    public void setMainApp(MainGUI mainGUI) {this.mainGUI = mainGUI;}

    public void startTable(){

        User actual = businessLogic.getCurrentUser();
        moneyLbl.setText(String.valueOf(businessLogic.getCurrency(actual.getUsername())));
        //eventTable.getItems().clear();
        //questionTable.getItems().clear();
        //resultTable.getItems().clear();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("event"));
        questionColumnID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        betData = FXCollections.observableArrayList();
        eventData = FXCollections.observableArrayList();
        questionData = FXCollections.observableArrayList();

        Fee f;
        for(Bet b: actual.getBetList()){
            betData.add(b);
            f = b.getFee();
           // Question q = f.getQuestion();
           // if (q!=null) {
           //     questionData.add(q);
           //     eventData.add(q.getEvent());
            }
        }


        //eventTable.getItems().addAll(eventData);
        //questionTable.getItems().addAll(questionData);
       //resultTable.getItems().addAll(betData);

    }
