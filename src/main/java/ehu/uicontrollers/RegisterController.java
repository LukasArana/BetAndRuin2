package ehu.uicontrollers;

import ehu.businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ehu.ui.MainGUI;

import java.util.ResourceBundle;

public class RegisterController implements Controller {

    private BlFacade businessLogic;

    private MainGUI mainGUI;

    @FXML
    private ResourceBundle resources;

    @FXML
    private CheckBox adminCheck;

    @FXML
    private TextField ageField;

    @FXML
    private Button alreadyAccountBtn;

    @FXML
    private Label answrLbl;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField usrField;

    public RegisterController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void goToLogin(ActionEvent event) {
        answrLbl.setText("");
        answrLbl.getStyleClass().clear();
        passField.clear();
        usrField.clear();
        surnameField.clear();
        emailField.clear();
        nameField.clear();
        ageField.clear();
        mainGUI.showLogin();
    }

    @FXML
    void registerClick(ActionEvent event) {
        if(usrField.getText().equals("")){
            answrLbl.setText(resources.getString("enterUsername"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(passField.getText().equals("")){
            answrLbl.setText(resources.getString("enterPassword"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(adminCheck.isSelected()){
            businessLogic.registerNewAdmin(usrField.getText(),passField.getText());
            answrLbl.setText(resources.getString("successRegister"));
            answrLbl.getStyleClass().setAll("lbl","lbl-success");
            return;
        }
        else if(nameField.getText().equals("")){
            answrLbl.setText(resources.getString("enterName"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(emailField.getText().equals("")){
            answrLbl.setText(resources.getString("enterEmail"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(surnameField.getText().equals("")){
            answrLbl.setText(resources.getString("enterSurname"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(ageField.getText().equals("")){
            answrLbl.setText(resources.getString("enterAge"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(!isInteger(ageField.getText())){
            answrLbl.setText(resources.getString("enterValAge"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(Integer.parseInt(ageField.getText()) < 18){
            answrLbl.setText(resources.getString("older18"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(!businessLogic.emailIsFree(emailField.getText())) {
            answrLbl.setText(resources.getString("emailInUse"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else if(!businessLogic.usernameIsFree(usrField.getText())){
            answrLbl.setText(resources.getString("usernameInUse"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
        else{
            businessLogic.registerNewUser(Integer.parseInt(ageField.getText()),usrField.getText(),passField.getText(),nameField.getText(),surnameField.getText(),emailField.getText());
            answrLbl.setText(resources.getString("successRegister"));
            answrLbl.getStyleClass().setAll("lbl","lbl-success");

        }
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
