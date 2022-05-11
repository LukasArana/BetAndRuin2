package ehu.uicontrollers;

import ehu.businessLogic.BlFacade;
import ehu.configuration.ConfigXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.TextAlignment;
import ehu.ui.MainGUI;

import java.net.URL;
import java.util.ResourceBundle;

public class UserGUIController implements Controller {

    @FXML
    private Button logOutButton;
    @FXML
    private Label answerLbl;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToggleGroup Idioms;

    @FXML
    private Button ShowMoveBtn;

    @FXML
    private Button browseBtn;

    @FXML
    private Button depositBtn;

    @FXML
    private Button placeBetBtn;

    private MainGUI mainGUI;

    private BlFacade businessLogic;


    @FXML
    void browseClick() {
        mainGUI.showBrowseQuestions();
        System.out.println("as");
    }

    @FXML
    void depositClick() {
        mainGUI.showDeposit();
    }

    @FXML
    void placeClick() {
        mainGUI.showPlace();
    }

    @FXML
    void showMovements() {;mainGUI.showMovements();}

    public UserGUIController(BlFacade bl) {
        businessLogic = bl;
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @FXML
    void logoutPressed(ActionEvent event) {
        mainGUI.showLogin();
    }

    @FXML void changeLanguage(ActionEvent event) {
        String language = ((RadioButton) Idioms.getSelectedToggle()).getText();

        switch (language){
            case "English":
                ConfigXML.getInstance().setLocale("en");
                answerLbl.setText("Restart to apply changes");
                break;
            case "Castellano":
                ConfigXML.getInstance().setLocale("es");
                answerLbl.setText("Reinicia para aplicar cambios");
                break;
            case "Euskera":
                ConfigXML.getInstance().setLocale("eus");
                answerLbl.setText("Berrireki aldaketak aplikatzeko");

                break;
        }
        answerLbl.setTextAlignment(TextAlignment.CENTER);
        answerLbl.getStyleClass().setAll("lbl","lbl-warning");

    }

}
