package ehu.uicontrollers;

import ehu.businessLogic.BlFacade;
import ehu.domain.Movement;
import ehu.domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ehu.ui.MainGUI;

import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;


public class ShowMovementsController implements Controller {

    private MainGUI mainGUI;

    ObservableList<Movement> data;

    private BlFacade businessLogic;

    @FXML
    private Button historyBtn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<Movement, Date> dateColumn;

    @FXML
    private TableColumn<Movement, String> balanceColumn;

    @FXML
    private TableColumn<Movement, String> eventColumn;

    @FXML
    private TableView<Movement> operationTable;

    @FXML
    void goBack(ActionEvent event) {
        mainGUI.showMain();
        operationTable.getItems().clear();
    }

    private void setupOperationSelection(){
        operationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println(operationTable.getSelectionModel().getSelectedItem());
            }
        });
    }
    public  void showHistory(){
        User actual = businessLogic.getCurrentUser();
        operationTable.getItems().clear();

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("event"));

        Date now = new Date();

        data = FXCollections.observableArrayList();
        //Collections.reverse(data);
        for (Movement m: actual.getMovements()){
            data.add(m);
        }

        Collections.reverse(data);

      /*  data.addAll(
                new Movement(now, "Barça-Athletic", 15f),
                new Movement(now, "real-osasuna", -5f)
        );*/


        operationTable.getItems().addAll(data);
        //operationTable.getItems().add();

        //setupOperationSelection();

    }
    @FXML
    void history(ActionEvent event) {
        showHistory();
    }

    public ShowMovementsController(BlFacade bl) {
        businessLogic = bl;
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }


}
