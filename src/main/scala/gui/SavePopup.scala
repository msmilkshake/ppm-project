package gui

import io.{IOUtils, Serializer}
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, TextField}

class SavePopup {
  
  @FXML
  var lblInvalid: Label = _
  @FXML
  var txtSave: TextField = _
  @FXML
  var btnSave: Button = _
  @FXML
  var btnCancel: Button = _

  SavePopup.instance = this
  
  
  @FXML
  def initialize(): Unit = {
    txtSave.focusedProperty().addListener((_, _, isFocused) =>
      isFocused.asInstanceOf[Boolean] match {
        case true =>
          lblInvalid.setText("")
        case false =>
      })
  }
  
  def btnSaveOnClicked(): Unit = {
    txtSave.getText.matches("\\w+[\\s\\w]*") match {
      case true =>
        Serializer.serializeContainer(MainWindow.c, txtSave.getText)
        close()
      case false =>
        lblInvalid.setText("Invalid save filename.")
    }
    }
  }
  
  def btnCancelOnClicked(): Unit = {
    close()
  }
  
  def close(): Unit = {
    btnCancel.getScene.getWindow.hide()
  }

}

object SavePopup {
  var instance: SavePopup = _
}
