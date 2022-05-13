package ehu.ui;

import ehu.businessLogic.BlFacade;
import ehu.uicontrollers.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUI {

  private Window mainLag,lostPassword,changePassword, userLag, createQuestionLag, browseQuestionsLag, loginWin, registerWin, setFeesWin, createEventsWin, placeBetWin, showMoves, depositMoney, removeBet, publishResultsWin, removeEventWin;


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

  public void setTitle(String title) {
    stage.setTitle(title);
  }




    class Window {
    Controller c;
    Parent ui;
  }

  private Window load(String fxmlfile) throws IOException {
    Window window = new Window();
    FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource(fxmlfile), ResourceBundle.getBundle("Etiquetas", Locale.getDefault()));
    loader.setControllerFactory(controllerClass -> {
      try{
        System.out.println(controllerClass.getConstructor(BlFacade.class));
        return controllerClass.getConstructor(BlFacade.class).newInstance(businessLogic);
      } catch (Exception e){
        throw new RuntimeException(e);
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
    createEventsWin = load("/CreateEvents.fxml");
    userLag = load("/UserGUI.fxml");
    showMoves = load("/showMovements.fxml");
    placeBetWin = load("/PlaceABet.fxml");
    depositMoney = load("/DepositMoney.fxml");
    removeBet = load("/RemoveBet.fxml");
    publishResultsWin = load("/publishResults.fxml");
    lostPassword = load("/lostPassword.fxml");
    changePassword = load("/ChangePassword.fxml");
    removeEventWin = load("/RemoveEvent.fxml");

    showLogin();

  }

//This method will only be called from LoginController
  public void showMain(String username){
    businessLogic.setUser(username);
    this.showMain();
  }
  public void showMain(){
    if (businessLogic.isAdmin()) {
      setupScene(mainLag.ui, "MainTitle", 356, 370);
    } else{
      setupScene(userLag.ui, "userTitle", 340, 360);
    }
  }

  public void showBrowseQuestions() {
    setupScene(browseQuestionsLag.ui, "BrowseQuestions", 1000, 500);
  }
  public void showLostPassword(){setupScene(lostPassword.ui, "LostPassword",355,210);}

  public void showCreateQuestion() {
    setupScene(createQuestionLag.ui, "CreateQuestion", 550, 400);
  }

  public void showLogin(){
    setupScene(loginWin.ui,"Login",387,240);
  }

  public void showRegister(){setupScene(registerWin.ui,"Register",466,303);}

  public void showSetFees(){setupScene(setFeesWin.ui,"Set Fees",600,454);}
  //public void showUser(){setupScene(userLag.ui,"MainTitle",332,281);}
  public void showPlace(){setupScene(placeBetWin.ui,"PlaceBet",820,500);}
  public void showCreateEvents(){setupScene(createEventsWin.ui, "CreateEvent", 446, 302);}
  public void showMovements(){
    ((ShowMovementsController)showMoves.c).showHistory();
    setupScene(showMoves.ui, "ShowMovements", 350,310);
  }
  public void showChangePassword(String usr,Integer code) {
    ChangePasswordController cont = ((ChangePasswordController) changePassword.c);
    cont.setCode(code);
    cont.setUser(usr);
    setupScene(changePassword.ui, "ChangePassword", 350,215);

  }
  public void showDeposit(){setupScene(depositMoney.ui, "DepositMoney", 427, 265);}
  public void showPublishResults(){setupScene(publishResultsWin.ui,"PublishResults",640,462);}
  public void showRemoveBet(){setupScene(removeBet.ui, "RemoveBet", 670, 500);}
  public void showRemoveEvent(){setupScene(removeEventWin.ui,"RemoveEvent", 590, 430);}

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
    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
  }
  public String getUsername(){
    return businessLogic.getCurrentUser().getUsername();
  }

}
