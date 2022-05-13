package ehu.ui;

import ehu.businessLogic.BlFacade;
import ehu.businessLogic.BlFacadeImplementation;
import ehu.configuration.ConfigXML;

//import javax.xml.ws.Service;
import java.util.Locale;

public class ApplicationLauncher {

  public static void main(String[] args) {

    ConfigXML config = ConfigXML.getInstance();

    Locale.setDefault(new Locale(config.getLocale()));
    System.out.println("Locale: " + Locale.getDefault());

    BlFacade businessLogic;

    try {
      //In order to make the executable files, we decided to make the businessLogic local
      businessLogic = BlFacadeImplementation.getInstance();

      new MainGUI(businessLogic);

    }
    catch (Exception e) {
      System.err.println("Error in ApplicationLauncher: " + e);
    }
  }
}
