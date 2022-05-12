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

public class MainGUIController implements Controller {

    @FXML
    private Label answerLbl;

    @FXML
    private Label selectOptionLbl;

    @FXML
    private Button browseQuestionsBtn;

    @FXML
    private Button createQuestionBtn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private MainGUI mainGUI;

    @FXML
    private Button setFeesBtn;


    @FXML
    private RadioButton englishRB;

    @FXML
    private RadioButton euskaraRB;

    @FXML
    private RadioButton castellanoRB;

    @FXML
    private ToggleGroup g1;

    @FXML
    private Button publishBtn;

    @FXML
    private Button createEventBtn;

    @FXML
    private Button removeEventBt;

    //@FXML
    //void userLogIn(ActionEvent event){ mainGUI.showUser();}

    @FXML
    void browseQuestions(ActionEvent event) {
        mainGUI.showBrowseQuestions();
    }

    @FXML
    void createQuestion(ActionEvent event) {
        mainGUI.showCreateQuestion();
    }
    @FXML
    void logOut(ActionEvent event){
        answerLbl.setText("");
        answerLbl.getStyleClass().clear();
        mainGUI.showLogin();
    }

    @FXML
    void publishResults(ActionEvent event) {
        mainGUI.showPublishResults();
    }

    @FXML
    void setFeesClick(ActionEvent event){mainGUI.showSetFees();}

    @FXML
    void createEvent(ActionEvent event) {mainGUI.showCreateEvents();}
    @FXML
    void removeEvent(ActionEvent event) {mainGUI.showRemoveEvent();}

    @FXML void changeLanguage(ActionEvent event) {
        String language = ((RadioButton) g1.getSelectedToggle()).getText();

        switch (language){
            case "English":
                ConfigXML.getInstance().setLocale("en");
                answerLbl.setText("Restart to apply changes");
                break;
            case "Castellano":
                ConfigXML.getInstance().setLocale("es");
                answerLbl.setText("Reinicia para aplicar cambios");
                break;
            case "Euskara":
                ConfigXML.getInstance().setLocale("eus");
                answerLbl.setText("Berrireki aldaketak aplikatzeko");

                break;
        }
        answerLbl.setTextAlignment(TextAlignment.CENTER);
        answerLbl.getStyleClass().setAll("lbl","lbl-warning");

    }


    /*
    @FXML
    void putCastellano(ActionEvent event) {
        try {
            // input the (modified) file content to the StringBuffer "input"

            BufferedReader file = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("config.xml").toString()));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            int i=1;

            while ((line = file.readLine()) != null) {
                if(i==16){
                    line = "  <locale>es</locale>"; // replace the line here
                }
                inputBuffer.append(line);
                inputBuffer.append('\n');
                i++;
            }
            file.close();

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(getClass().getClassLoader().getResource("config.xml").toString());
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }

        //Locale.setDefault(new Locale("es"));
        //System.out.println("Locale: " + Locale.getDefault());
        //redraw();
    }

    @FXML
    void putEnglish(ActionEvent event) {
        Locale.setDefault(new Locale("en"));
        System.out.println("Locale: " + Locale.getDefault());
        redraw();
    }

    @FXML
    void putEuskara(ActionEvent event) {
        Locale.setDefault(new Locale("eus"));
        System.out.println("Locale: " + Locale.getDefault());
        redraw();
    }

    */

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
    public Controller newInstance(BlFacade businessLogic){
        return this;
    }
    public MainGUIController(BlFacade businessLogic){

    }
}
