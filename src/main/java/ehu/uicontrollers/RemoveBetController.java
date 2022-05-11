package ehu.uicontrollers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import ehu.businessLogic.*;
import ehu.domain.Movement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ehu.ui.MainGUI;

public class RemoveBetController implements Controller{

    private MainGUI mainGUI;

    private BlFacadeImplementation businessLogic;

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

            errorLbl.setText("You must select a bet to delete");
        }else {
            removeTable.getItems().remove(removeTable.getSelectionModel().getSelectedItem());

            moneyLbl.setText(String.valueOf(removeTable.getSelectionModel().getSelectedItem().getBalance() + businessLogic.getCurrency(mainGUI.getUsername())));
        }
    }

    @FXML
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'RemoveBet.fxml'.";
        assert currentBalanceLbl != null : "fx:id=\"currentBalanceLbl\" was not injected: check your FXML file 'RemoveBet.fxml'.";
        assert dateColumn != null : "fx:id=\"dateColumn\" was not injected: check your FXML file 'RemoveBet.fxml'.";
        assert eventColumn != null : "fx:id=\"eventColumn\" was not injected: check your FXML file 'RemoveBet.fxml'.";
        assert importedMoneyColumn != null : "fx:id=\"importedMoneyColumn\" was not injected: check your FXML file 'RemoveBet.fxml'.";
        assert moneyLbl != null : "fx:id=\"moneyLbl\" was not injected: check your FXML file 'RemoveBet.fxml'.";
        assert removeBtn != null : "fx:id=\"removeBtn\" was not injected: check your FXML file 'RemoveBet.fxml'.";
        assert removeTable != null : "fx:id=\"removeTable\" was not injected: check your FXML file 'RemoveBet.fxml'.";

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}