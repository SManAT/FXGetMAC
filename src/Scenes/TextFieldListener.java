package Scenes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 *
 * @author Stefan
 */
public class TextFieldListener implements ChangeListener<Boolean> {
  private final TextField textField;

  public TextFieldListener(TextField textField) {
    this.textField = textField;
  }

  @Override
  public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    //focus?
    if (newValue){
      this.textField.getStyleClass().add("textfield_edit");
    }else{
      this.textField.getStyleClass().remove("textfield_edit");
    }
  }

}
