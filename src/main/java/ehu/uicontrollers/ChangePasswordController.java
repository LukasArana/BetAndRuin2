package ehu.uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;

import ehu.businessLogic.BlFacade;
import ehu.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class ChangePasswordController implements Controller{
    private MainGUI mainGUI;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    private BlFacade businessLogic;
    @FXML
    private Label answrLbl;


    @FXML
    private TextField codeField;

    @FXML
    private TextField newPassordField;

    @FXML
    private PasswordField repetedPasswordField;
    private Integer code;
    private String user;

    public void setCode(Integer code){
        this.code = code;
    }
    public void setUser(String user){
        this.user = user;
    }
    @FXML
    void changePasswordPressed(ActionEvent event) {
        if (Integer.parseInt(codeField.getText()) == this.code){
            if (newPassordField.getText().equals(repetedPasswordField.getText())){

                businessLogic.setPassword(user, newPassordField.getText());
            }else{
                answrLbl.setText("ERROR: PASSWORDS ARE NOT EQUAL");
            }
        } else{
            answrLbl.setText("ERROR: CODE IS NOT EQUAL");
        }
    }
    @FXML
    void logInPressed(ActionEvent event) {
        mainGUI.showLogin();
    }

    @FXML
    void initialize() {
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
    public ChangePasswordController(BlFacade businessLogic){
        this.businessLogic = businessLogic;
    }
}
