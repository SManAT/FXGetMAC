package Utils;

import java.util.Arrays;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Übersetzt den KeyCode in einen String
 * @author Mag. Stefan Hagmann
 */
public class Keyboard {
  
  public static String getString(KeyEvent event){
    String erg=null;
    KeyCode[] keyValues = KeyCode.values();
    List<KeyCode> keyList = Arrays.asList(keyValues);
    int index = keyList.indexOf(event.getCode());
    if(index!=-1){
      KeyCode key = keyList.get(index);
      erg = key.getName();
      if (event.getCode().isLetterKey() && !event.isShiftDown()) {
        erg = erg.toLowerCase();
      }
    }else{
      erg=null;
    }
    if(event.getCode().isFunctionKey() || event.getCode().isMediaKey() || 
            event.getCode().isModifierKey() || event.getCode().isNavigationKey()){
      erg = null;
    }
    if(event.getCode().isWhitespaceKey()){
      erg = " ";
    }
   
    return erg;
  }
  
}
