package fxgetmac;

import FX.FXSystem;
import MySQL.MySQLWorker;
import SH.Xml.XMLTool;
import Scenes.MainSceneController;
import crypt.CryptClass;
import java.nio.file.Paths;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import xml.configHandler;

/**
 *
 * @author Mag. Stefan Hagmann
 */
public class FXGetMAC extends Application {

  final static String configFileName = "config.xml";
  private static MySQLWorker mysql;
  private static String last;
  private static String SQLCmd;
  private Pane rootLayout;
  private MainSceneController controller;
  private static String pingIP;

  @Override
  public void start(Stage primaryStage) {
    LoadConfig();
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/Scenes/MainScene.fxml"));
      rootLayout = loader.load();
      controller = loader.getController();
    } catch (Exception ex) {
      Logger.getLogger("FXML not found");
      ex.printStackTrace();
    }

    primaryStage.getIcons().add(new Image("file:icon.png"));
    primaryStage.setTitle("Get the MAC of the Computer, (c) Mag. Stefan Hagmann");

    // Show the scene containing the root layout.
    Scene scene = new Scene(rootLayout);
    primaryStage.setScene(scene);

    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    FXSystem.StageSize(primaryStage,
            primScreenBounds.getWidth() * 0.33,
            primScreenBounds.getHeight()
    );
    primaryStage.setX(0);
    primaryStage.setY(0);

    primaryStage.show();
       
    controller.StartController(this.SQLCmd, this.pingIP, configFileName, this.last);
    //KeyPressed an Textflow binden
    scene.onKeyPressedProperty().bind(
      controller.getTextflowLogger().onKeyPressedProperty()
    );
  }

  @Override
  /**
   * Called when Application ist closed with Platform.exit();
   */
  public void stop() throws Exception {
    super.stop();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Lädt aus einer XML Datei alle Config einstellungen
   */
  private static void LoadConfig() {
    try {
      configHandler handle = new configHandler();
      String configFile = Paths.get(configFileName).toAbsolutePath().toString();

      XMLTool.LoadXMLFile(configFile, handle);
      //mysql = new MySQLWorker();
      SQLCmd = handle.cmd;
      pingIP = handle.pingIP;
      last = handle.last;
      
    } catch (Exception ex) {
      ex.printStackTrace();
      System.exit(1);
    }
  }
}
