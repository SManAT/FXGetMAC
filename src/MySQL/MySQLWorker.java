package MySQL;

import crypt.CryptClass;
import fxgetmac.MAC;

/**
 * Klasse um MAC in DB einzutragen
 * @author Stefan
 */
public class MySQLWorker {
  /**
   * MySQL aktiv?
   */
  private boolean active;
  private String server;
  private String user;
  private String password;
  private String database;
  /**
   * Tabelle in die eingetragen wird
   */
  private String table;
  private String cryptString;

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean activate) {
    this.active = activate;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public String getCryptString() {
    return cryptString;
  }
  

  public void setPassword(String pwd) {
    this.cryptString = pwd;
    CryptClass crypt = new CryptClass();
    this.password = crypt.decrypt(pwd);
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public void UpdateDB(MAC mac, String eingabe) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  
  
  
}
