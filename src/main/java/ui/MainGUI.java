package ui;

import businessLogic.BlFacade;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import uicontrollers.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUI {

  private Window mainLag, createQuestionLag, browseQuestionsLag, loginWin, registerWin, setFeesWin, createEventsWin;

  private BlFacade businessLogic;
  private Stage stage;
  private Scene scene;

  public BlFacade getBusinessLogic() {
    return businessLogic;
  }

  public void setBusinessLogic(BlFacade afi) {
    businessLogic = afi;
  }

  public MainGUI(BlFacade bl) {
    Platform.startup(() -> {
      try {
        setBusinessLogic(bl);
        init(new Stage());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }


  class Window {
    Controller c;
    Parent ui;
  }

  private Window load(String fxmlfile) throws IOException {
    Window window = new Window();
    FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource(fxmlfile), ResourceBundle.getBundle("Etiquetas", Locale.getDefault()));
    loader.setControllerFactory(controllerClass -> {

      if (controllerClass == BrowseQuestionsController.class) {
        return new BrowseQuestionsController(businessLogic);
      }
      if (controllerClass == CreateQuestionController.class) {
        return new CreateQuestionController(businessLogic);
      }
      if(controllerClass == LoginController.class){
        return new LoginController(businessLogic);
      }
      if(controllerClass == RegisterController.class){
        return new RegisterController(businessLogic);
      }
      if(controllerClass == setFeesController.class){
        return new setFeesController(businessLogic);
      }
      if(controllerClass == CreateEventsController.class){
        return new CreateEventsController(businessLogic);
      }
      else {
        // default behavior for controllerFactory:
        try {
          return controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception exc) {
          exc.printStackTrace();
          throw new RuntimeException(exc); // fatal, just bail...
        }
      }


    });
    window.ui = loader.load();
    ((Controller) loader.getController()).setMainApp(this);
    window.c = loader.getController();
    return window;
  }

  public void init(Stage stage) throws IOException {

    this.stage = stage;

    mainLag = load("/MainGUI.fxml");
    browseQuestionsLag = load("/BrowseQuestions.fxml");
    createQuestionLag = load("/CreateQuestion.fxml");
    loginWin = load("/Login.fxml");
    registerWin = load("/Register.fxml");
    setFeesWin = load("/setFees.fxml");
    createEventsWin = load("/createEvents.fxml");

    showLogin();

  }

//  public void start(Stage stage) throws IOException {
//      init(stage);
//  }


  public void showMain(){
    setupScene(mainLag.ui, "MainTitle", 320, 290);
  }

  public void showBrowseQuestions() {
    setupScene(browseQuestionsLag.ui, "BrowseQuestions", 1000, 500);
  }

  public void showCreateQuestion() {
    setupScene(createQuestionLag.ui, "CreateQuestion", 550, 400);
  }

  public void showLogin(){
    setupScene(loginWin.ui,"Login",355,240);
  }

  public void showRegister(){setupScene(registerWin.ui,"Register",466,303);}

  public void showSetFees(){setupScene(setFeesWin.ui,"Set Fees",600,454);}

  public void showCreateEvents(){setupScene(createEventsWin.ui, "CreateEvent", 446, 302);}

  private void setupScene(Parent ui, String title, int width, int height) {
    if (scene == null){
      scene = new Scene(ui, width, height);
      scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
      stage.setScene(scene);
    }
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setTitle(ResourceBundle.getBundle("Etiquetas",Locale.getDefault()).getString(title));
    scene.setRoot(ui);
    stage.show();
  }

//  public static void main(String[] args) {
//    launch();
//  }
}
