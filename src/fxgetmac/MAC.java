package fxgetmac;

import SH.NET.NETUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class MAC {

  
  private ArrayList<String> macs;
  private final String pingIP;

  public MAC(String pingIP) {
    //CIDR
    this.pingIP = pingIP;
    macs = new ArrayList<>();
    NETUtils net = new NETUtils();
    macs = net.getAllMAC(this.pingIP);
  }

  public ArrayList<String> getMacs() {
    return macs;
  }

  public void setMacs(ArrayList<String> macs) {
    this.macs = macs;
  }

  /**
   * Erste Mac aus Liste
   *
   * @return
   */
  public String getFirstMac() {
    try {
      return this.macs.get(0);
    } catch (IndexOutOfBoundsException e) {
      return "";
    }
  }

  /**
   * String Liste der MAC Adressen
   *
   * @return
   */
  public String getMacListasString() {
    try {
      String erg = "";
      Iterator<String> iterator = macs.iterator();
      while (iterator.hasNext()) {
        String mac = iterator.next();
        erg += mac + ";  ";
      }
      return erg.substring(0, erg.length() - 3);
    } catch (Exception ex) {
      return "Error";
    }
  }

  /**
   * MAC Adressen gefunden?
   * @return Anzahl
   */
  public int getLength() {
    return this.macs.size();
  }
  
}
