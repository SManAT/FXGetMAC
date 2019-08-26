package Scenes;

import Utils.ExecEditor;
import Utils.Textfile;
import MySQL.MySQLWorker;
import SH.FX.TextFlowLogger.TextFlowLogger;
import SH.Xml.XMLTool;
import Utils.Keyboard;
import Utils.WaitNClose;
import fxgetmac.MAC;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import org.w3c.dom.Element;

/**
 * FXML Controller class
 *
 * @author Mag. Stefan Hagmann
 */
public class MainSceneController implements Initializable {

  @FXML
  private ScrollPane textflowPane;
  @FXML
  private TextFlow textflow;

  private TextFlowLogger textflowLogger;
  private final int fontsize = 16;
  private MAC mac;
  private MySQLWorker mysql;
  private String last;
  private String eingabe;

  //Arbeitsschritte
  private int state = 0;
  private String pingIP;
  private Textfile textfile;
  private String SQLCmd;
  private String configFileName;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

  }
	
	public void StartController(String SQLCmd, String pingIP, String configFileName, String last) {
    this.pingIP = pingIP;
    this.SQLCmd = SQLCmd;
    this.configFileName = configFileName;
    this.last = last;
    
    
    textflowLogger = new TextFlowLogger(textflow, this.fontsize);

    /* Autoscroll */
    textflow.getChildren().addListener(
            (ListChangeListener<Node>) ((change) -> {
              textflow.layout();
              textflowPane.layout();
              textflowPane.setVvalue(1.0f);
            }));

    textflowLogger.AppendHighlight("MAC Tool, (c) Mag. Stefan Hagmann");
    textflowLogger.AppendHighlight("---------------------------------");
    textflowLogger.Append("Checking active eth's, Mac Adresses ...");
    textflowLogger.Append("");
    textflowLogger.Append("Trying to PING " + pingIP);

    //MAC Adr laden über Ping IP
    mac = new MAC(pingIP);
    //Etwas gefunden?
    if (mac.getLength() > 0) {
      textflowLogger.Append(mac.getMacListasString());
      textflowLogger.setColor(Color.YELLOW);
      textflowLogger.Append("Letzte Eingabe mit <TAB> einfügen ...");
      textflowLogger.Append("");
      textflowLogger.resetColor();
      
      textflowLogger.Append("Welcher Hostname soll zugeordnet werden? [" + last + "]:", TextFlowLogger.NOLINEBREAK);
      textflowLogger.startReading();
      state = 1;
    } else {
      textflowLogger.AppendError("Abbruch", "Keine MAC Adresse gefunden, PING IP korrekt?");
    }
    
    textflowLogger.getTextFlow().setOnKeyPressed((KeyEvent event) -> {
      if (textflowLogger.isReading()) {
        //Eingabe beendet
        if (event.getCode() == KeyCode.ENTER) {
          eingabe = textflowLogger.getEingabe();
          textflowLogger.resetEingabe();
          //Hostname eingegeben
          if (state == 1) {
            if (eingabe.compareTo("") == 0) {
              textflowLogger.Append(last);
              eingabe = last;
            }
            this.last = eingabe;
            textflowLogger.Append("");
            state = 0;
            this.HostnameEntered();
          }
        } else {
          switch (event.getCode()) {
            case TAB:
              textflowLogger.AppendInline(last);
              textflowLogger.setEingabe(last);
              break;
            case BACK_SPACE:
              //Letztes Zeichen löschen
              textflowLogger.removelastChar();
              break;
            default:
              String ch = Keyboard.getString(event);
              if (ch != null) {
                textflowLogger.addToEingabe(ch);
                textflowLogger.AppendInline(ch);
              } break;
          }
        }
      }
      //kein Bubbling event ist verbraucht
      event.consume();
    });
    
   
    
  }

  public TextFlow getTextflowLogger() {
    return textflow;
  }

  /**
   * Hostname wurde eingegegben
   */
  private void HostnameEntered() {
    //in config sichern
    Save_config();
    
    textfile = new Textfile(textflowLogger);
    textflowLogger.Append("Saving to Textfile... " + textfile.getFilename()+", "+textfile.getSQLFilename());
    textfile.writeFile(mac, eingabe, SQLCmd);
    
    textflowLogger.AppendInfo("FERTIG", "Programm kann nun geschlossen werden");

    BlockingQueue queue = new ArrayBlockingQueue(2);
    try {
      //queue.put("C:\\Program Files (x86)\\Geany\\bin\\geany.exe");
      queue.put("notepad.exe");
    } catch (InterruptedException ex) {}

    //ExecEditor execEditor = new ExecEditor(queue, textfile.getFilename());
    //new Thread(execEditor).start();
    
    //ExecEditor execEditor = new ExecEditor(queue, textfile.getSQLFilename());
    //new Thread(execEditor).start();
    
		
		
		WaitNClose waitNClose = new WaitNClose(textflowLogger);
		waitNClose.setOnSucceeded((event) -> {
			Platform.exit();
		});
		new Thread(waitNClose).start();
		
		
  }

  private void Save_config() {
    XMLTool xml = new XMLTool();

    //create the root element <Books>
    Element rootEle = xml.dom.createElement("config");
    xml.dom.appendChild(rootEle);
    
    Element child = xml.dom.createElement("common");
    rootEle.appendChild(child);
    
    Element item = xml.createNode("pingIP", this.pingIP);
    child.appendChild(item);
    item = xml.createNode("last", this.last);
    child.appendChild(item);

    
    child = xml.dom.createElement("mysql");
    rootEle.appendChild(child);

    item = xml.createNode("cmd", this.SQLCmd);
    child.appendChild(item);
    //ins File
    xml.writeXML(configFileName);
  }

  
  

}
