package ehu.uicontrollers;

import ehu.businessLogic.BlFacade;
import ehu.ui.MainGUI;

public interface Controller {
  void setMainApp(MainGUI mainGUI);
  //Controller newInstance(BlFacade businessLogic);
}
