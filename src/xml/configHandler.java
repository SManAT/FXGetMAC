package xml;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Ladet Einstellungen
 *
 * @author Mag. Stefan Hagmann
 */
public class configHandler implements ContentHandler {

  private String currentValue;
  private boolean mysql=false;
  private String activate;
  public String server;
  public String user;
  public String password;
  public String database;
  public String table;
  private boolean common;
  public String pingIP;
  public String last;
    
  public configHandler() {

  }

  // Aktuelle Zeichen die gelesen werden, werden in eine Zwischenvariable
  // gespeichert
  @Override
  public void characters(char[] ch, int start, int length)
          throws SAXException {
    currentValue = new String(ch, start, length);
  }

  // Methode wird aufgerufen wenn der Parser zu einem Start-Tag kommt
  @Override
  public void startElement(String uri, String localName, String qName,
          Attributes atts) throws SAXException {
    if (localName.equals("config")) {
    }
    if (localName.equals("mysql")) {
      mysql = true;
    }
    if (localName.equals("common")) {
      common = true;
    }
  }

  // Methode wird aufgerufen wenn der Parser zu einem End-Tag kommt
  @Override
  public void endElement(String uri, String localName, String qName)
          throws SAXException {
    if(mysql==true){
      if (localName.equals("activate")) {
        activate = currentValue.trim();
      }
      if (localName.equals("server")) {
        server = currentValue.trim();
      }
      if (localName.equals("user")) {
        user = currentValue.trim();
      }
      if (localName.equals("password")) {
        password = currentValue.trim();
      }
      if (localName.equals("database")) {
        database = currentValue.trim();
      }
      if (localName.equals("table")) {
        table = currentValue.trim();
      }
    }
    if(common==true){
      if (localName.equals("pingIP")) {
        pingIP = currentValue.trim();
      }
      if (localName.equals("last")) {
        last = currentValue.trim();
      }
    }
    
    
    
    if (localName.equals("mysql")) {
      mysql = false;
    }
    if (localName.equals("common")) {
      common = false;
    }
    
  }

  @Override
  public void endDocument() throws SAXException {
  }

  @Override
  public void endPrefixMapping(String prefix) throws SAXException {
  }

  @Override
  public void ignorableWhitespace(char[] ch, int start, int length)
          throws SAXException {
  }

  @Override
  public void processingInstruction(String target, String data)
          throws SAXException {
  }

  @Override
  public void setDocumentLocator(Locator locator) {
  }

  @Override
  public void skippedEntity(String name) throws SAXException {
  }

  @Override
  public void startDocument() throws SAXException {
  }

  @Override
  public void startPrefixMapping(String prefix, String uri)
          throws SAXException {
  }

  public boolean getActivate() {
    if(activate.compareToIgnoreCase("true")==0){
      return true;
    }else{
      return false;
    }
  }
}
