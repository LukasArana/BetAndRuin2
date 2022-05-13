package ehu.uicontrollers;
import ehu.utils.SendMail;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Random;
import ehu.businessLogic.BlFacade;
import ehu.ui.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LostPasswordController implements Controller {
    BlFacade businessLogic;
    MainGUI mainGUI;
    SendMail sender;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label answrLbl;

    @FXML
    private TextField emailField;

    @FXML
    private Button backBtn;

    @FXML
    private TextField usrField;

    public LostPasswordController(BlFacade businessLogic) {
        this.businessLogic = businessLogic;
    }

    @FXML
    void goBack(ActionEvent event) {
        emailField.clear();
        usrField.clear();
        answrLbl.setText("");
        answrLbl.getStyleClass().clear();
        mainGUI.showLogin();
    }

    @FXML
    void newPassword(ActionEvent event) {
        String email = emailField.getText();
        String user = usrField.getText();
        if (!businessLogic.usernameIsFree(user)) {
            if (user.equals("admin") ) {
                answrLbl.setText("Why do you want to change admin password :)");
            } else if(user.equals("user")){
                answrLbl.setText("Do not try to change default user password jajaja");
            } else{
                if (businessLogic.getEmail(user).equals(email)) {
                    sender = new SendMail();
                    Random rand = new Random();
                    int code = rand.nextInt(999999); //Make it simple :)
                    sender.sendMail(email, user, code);

                    mainGUI.showChangePassword(user, code);
                } else {
                    answrLbl.setText(resources.getString("UsrNotConnected"));
                    answrLbl.getStyleClass().setAll("lbl","lbl-danger");
                }
            }

        }else{
            answrLbl.setText(resources.getString("UsrNotConnected"));
            answrLbl.getStyleClass().setAll("lbl","lbl-danger");
        }
    }

    @FXML
    void initialize() {

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
