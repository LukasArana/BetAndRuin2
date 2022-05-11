package ehu.uicontrollers;

import ehu.businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ehu.ui.MainGUI;

import java.util.ResourceBundle;

public class LoginController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label answrLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passField;

    @FXML
    private Button registerBtn;

    private MainGUI mainGUI;

    @FXML
    private TextField usrField;

    private BlFacade businessLogic;

    public LoginController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void loginAction(ActionEvent event) {
//        if(usrField.getText().equals("")){
//            answrLbl.setText("You must enter a username");
//            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
//        }
//        else if(passField.getText().equals("")){
//            answrLbl.setText("You must enter a password");
//            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
//        }
//        else{
//            mainGUI.showMain();
//        }
        if (usrField.getText().isBlank() || passField.getText().isBlank()) { //No values in text fields
            answrLbl.setText(resources.getString("usPass"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
            passField.setText("");
        }
        else if (!businessLogic.checkLogIn(usrField.getText(), passField.getText())) {
            answrLbl.setText(resources.getString("invalidCredentials"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
            passField.setText("");
        } else { //Valid credentials
            mainGUI.showMain(usrField.getText());
        }
    }
    @FXML
    void registerAction(ActionEvent event) {
        usrField.clear();
        answrLbl.setText("");
        answrLbl.getStyleClass().clear();
        passField.clear();
        mainGUI.showRegister();
    }
    @FXML
    void lostPasswordPressed(ActionEvent event){
        mainGUI.showLostPassword();
    }


    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
