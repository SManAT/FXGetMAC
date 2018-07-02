package Utils;

import SH.FX.TextFlowLogger.TextFlowLogger;
import fxgetmac.MAC;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan
 */
public class Textfile {
  String filename;
  ArrayList<String> lines = new ArrayList<>();
  ArrayList<String> linesSQL = new ArrayList<>();
  private final TextFlowLogger textflowLogger;
  private final String filenameSQL;

  public Textfile(TextFlowLogger textflowLogger) {
    this.filename = "macScan.csv";
    this.filenameSQL = "macScan.sql";
    this.textflowLogger = textflowLogger;
    lines.clear();
    linesSQL.clear();
  }

  public String getFilename() {
    return filename;
  }
  
  public String getSQLFilename() {
    return filenameSQL;
  }
  
  /**
   * Schreibt die MAC in die CSV Datei erstellt aber keine doppelten Eintragungen
   * @param mac
   * @param eingabe 
   */
  public void writeFile(MAC mac, String eingabe) {
    this.createCSVString(mac, eingabe);
    this.createSQLString(mac, eingabe);
    try {
      Path path = Paths.get(filename);
      Files.write(path, lines,
              StandardOpenOption.CREATE, StandardOpenOption.APPEND);
      Path pathsql = Paths.get(filenameSQL);
      Files.write(pathsql, linesSQL,
              StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    } catch (IOException ex) {
      textflowLogger.AppendError("Kann nicht Datei "+filename+" erzeugen!");
    }
  }
  
  private LinkedList<String> ReadFile(String filename, boolean clearcomments) {
    LinkedList<String> erg = new LinkedList<String>();
    BufferedReader rd;
    try {
      rd = new BufferedReader(new FileReader(filename));    //bis zum Ende der Datei lesen
      String buffer;
      while ((buffer = rd.readLine()) != null) {
        //String UTF8Str = new String(buffer.getBytes(),"UTF-8");
        if (clearcomments) {
          if (buffer.startsWith("#") == false) {
            erg.add(buffer);
          }
        } else {
          erg.add(buffer);
        }
      }
      rd.close();
    } catch (FileNotFoundException ex) {
      System.out.println("Datei " + filename + " nicht gefunden");
    } catch (IOException ex) {
    }
    return erg;
  }

  /**
   * Übernimmt die MACS mit Namen als CSV
   * @param mac 
   */
  private void createCSVString(MAC mac, String eingabe) {
    //mit Clear Comments
    LinkedList<String> fileLines = ReadFile(filename,true);
    
    ArrayList<String> macs = mac.getMacs();
    macs.forEach((item) -> { 
      //Gibt es das Element schon?
      int index = fileLines.indexOf(eingabe+";"+item);
      if(index==-1){
        lines.add(eingabe+";"+item);
      }
    });
  }

  private void createSQLString(MAC mac, String eingabe) {
    //mit Clear Comments
    LinkedList<String> fileLines = ReadFile(filenameSQL,true);
    
    ArrayList<String> macs = mac.getMacs();
    macs.forEach((item) -> { 
      //Gibt es das Element schon?
      int index = fileLines.indexOf(eingabe+";"+item);
      if(index==-1){
        linesSQL.add("INSERT INTO mac VALUES (null,'"+eingabe+"','"+item+"',-1);");
      }
    });
  }


  
  
}
