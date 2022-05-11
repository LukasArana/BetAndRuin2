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
    private PasswordField emailField;


    @FXML
    private TextField usrField;

    public LostPasswordController(BlFacade businessLogic) {
        this.businessLogic = businessLogic;
    }

    @FXML
    void newPassword(ActionEvent event) {
        String email = emailField.getText();
        String user = usrField.getText();
        if (!businessLogic.usernameIsFree(user)) {
            if (businessLogic.getEmail(user).equals(email)) {
                sender = new SendMail();
                Random rand = new Random();
                int code = rand.nextInt(999999); //Make it simple :)
                sender.sendMail(email, user, code);

                mainGUI.showChangePassword(user, code);
            } else {
                answrLbl.setText("The user is not connected to that email.");
            }
        }else{
            answrLbl.setText("The user is not connected to that email.");
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
