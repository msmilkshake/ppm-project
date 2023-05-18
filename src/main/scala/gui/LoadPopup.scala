package gui

import io.IOUtils
import javafx.fxml.FXML
import javafx.scene.control.{Button, Label, TextField}

class LoadPopup {
  
  @FXML
  var lblInvalid: Label = _
  @FXML
  var txtSave: TextField = _
  @FXML
  var btnLoad: Button = _
  @FXML
  var btnCancel: Button = _
  
  LoadPopup.instance = this
  
  var filename: Option[String] = None
  
  @FXML
  def initialize(): Unit = {
    txtSave.focusedProperty().addListener((_, _, isFocused) =>
      isFocused.asInstanceOf[Boolean] match {
        case true =>
          lblInvalid.setText("")
        case false =>
      })
  }
  
  def btnLoadOnClicked(): Unit = {
    filename = Some(txtSave.getText)
    IOUtils.checkSaveExists(filename.get) match {
      case false =>
        filename = None
        lblInvalid.setText("Invalid save filename.")
      case true =>
        close()
    }
  }
  
  def btnCancelOnClicked(): Unit = {
    close()
  }
  
  def close(): Unit = {
    btnCancel.getScene.getWindow.hide()
  }

}

object LoadPopup {
  var instance: LoadPopup = _
}
