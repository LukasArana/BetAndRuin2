package ehu.uicontrollers;

import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import ehu.businessLogic.*;
import ehu.domain.Movement;
import ehu.domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ehu.ui.MainGUI;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class RemoveBetController implements Controller{

    private MainGUI mainGUI;

    private BlFacade businessLogic;

    ObservableList<Movement> data;

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label errorLbl;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Label currentBalanceLbl;

    @FXML
    private TableColumn<Movement, Date> dateColumn;

    @FXML
    private TableColumn<Movement, String> eventColumn;

    @FXML
    private TableColumn<Movement, Float> importedMoneyColumn;

    @FXML
    private Label moneyLbl;

    @FXML
    private Button removeBtn;

    @FXML
    private TableView<Movement> removeTable;

    @FXML
    void goBack(ActionEvent event) {
        mainGUI.showMain();
        removeTable.getItems().clear();
    }

    @FXML
    void removeBet(ActionEvent event) {
        if (removeTable.getSelectionModel().getSelectedItem() == null){
            errorLbl.getStyleClass().setAll("lbl", "lbl-danger");
            errorLbl.setText("You must select a bet to delete");
        }else {
            Float actualBalance = businessLogic.getCurrency(mainGUI.getUsername()) -
                            removeTable.getSelectionModel().getSelectedItem().getBalance();
            moneyLbl.setText(String.valueOf(actualBalance));
            businessLogic.updateCurrency(actualBalance, mainGUI.getUsername());
            removeTable.getItems().remove(removeTable.getSelectionModel().getSelectedItem());

        }
    }


    public RemoveBetController(BlFacade businessLogic){this.businessLogic =  businessLogic;}

    @Override
    public void setMainApp(MainGUI mainGUI) {this.mainGUI = mainGUI;}

    public void startTable(){
        moneyLbl.setText(businessLogic.getCurrency(mainGUI.getUsername()).toString());

        User actual = businessLogic.getCurrentUser();
        removeTable.getItems().clear();

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("event"));
        importedMoneyColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        data = FXCollections.observableArrayList();

        Date now = new Date();

        for(Movement m: actual.getMovements()){
            Date betDate = businessLogic.getEventDate(m);
            if (m.getBalance() < 0 & now.before(betDate)){
                data.add(m);
            }
        }

        Collections.reverse(data);

        removeTable.getItems().addAll(data);

    }
}