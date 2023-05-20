package gui

import core.Difficulty.{Easy, Medium}
import core.{GameState, Settings}
import gui.Program.container
import io.IOUtils
import javafx.fxml.FXML
import javafx.scene.control._
import tui.Container

import scala.util.{Success, Try}

class SettingsWindow {

  @FXML
  private var rbEasy: RadioButton = _
  @FXML
  private var rbMedium: RadioButton = _
  @FXML
  private var txtBoardLength: TextField = _
  @FXML
  private var btnEraseAutoSave: Button = _
  @FXML
  private var btnSave: Button = _
  @FXML
  private var btnDiscard: Button = _

  SettingsWindow.instance = this

  @FXML
  def initialize(): Unit = {
    refreshInfo()
    txtBoardLength.focusedProperty().addListener((_, _, isFocused) =>
      isFocused.asInstanceOf[Boolean] match {
        case true =>
          txtBoardLength.setText("")
        case false =>
          txtBoardLength.getText match {
            case blank if blank.isBlank =>
              txtBoardLength.setText(f"${container.newGameSettings.boardLength}")
            case _ =>
          }
      })
  }

  def refreshInfo(): Unit = {
    container.newGameSettings.difficulty match {
      case Easy =>
        rbEasy.fire()
      case Medium =>
        rbMedium.fire()
    }
    txtBoardLength.setText(f"${container.newGameSettings.boardLength}")
  }

  def btnEraseAutoSaveOnClicked(): Unit = {
    val confirmation: Alert = new Alert(Alert.AlertType.CONFIRMATION)
    val dialogPane = confirmation.getDialogPane

    confirmation.setTitle("Confirmation")
    confirmation.setHeaderText("Are you sure you want to delete the autosave?")

    dialogPane.getStylesheets.add(getClass.getResource("styles.css").toExternalForm)
    dialogPane.getStyleClass.add("dialog")
    val headerLabel = dialogPane.lookup(".header")
    if (headerLabel != null) headerLabel.getStyleClass.add("dialog")

    confirmation.showAndWait().get() match {
      case ButtonType.OK =>
        IOUtils.deleteContinueFileQuiet()
        container = Container(
          GameState(
            None,
            container.newGameSettings.difficulty,
            container.gameState.random,
            None),
          Nil,
          container.programState,
          container.newGameSettings
        )
      case _ =>
    }
  }

  def btnSaveOnClicked(): Unit = {
    validateSettings() match {
      case true => goBack()
      case false =>
    }
  }

  def btnDiscardOnClicked(): Unit = {
    goBack()
  }

  def goBack(): Unit = {
    btnDiscard.getScene.setRoot(Program.mainViewRoot)
    MainWindow.instance.refreshInfo()
    Program.setWindowSizeAndCenter(Program.startWinWidth, Program.startWinHeight)
  }

  def validateSettings(): Boolean = {
    Try(txtBoardLength.getText.toInt) match {
      case Success(value) if value >= 4 && value <= 20 =>
        container = Container(
          container.gameState,
          container.playHistory,
          container.programState,
          Settings(value,
            rbEasy.isSelected match {
              case true => Easy
              case _ => Medium
            })
        )
        true

      case _ =>
        val alert = new Alert(Alert.AlertType.INFORMATION)
        val dialogPane = alert.getDialogPane

        alert.setTitle("Invalid Board Length")
        alert.setHeaderText("You have entered an invalid Board Length")
        alert.setContentText("The length of the board must be between 4 and 20.")

        dialogPane.getStylesheets.add(getClass.getResource("styles.css").toExternalForm)
        dialogPane.getStyleClass.add("dialog")
        val headerLabel = dialogPane.lookup(".header")
        if (headerLabel != null) headerLabel.getStyleClass.add("dialog")

        val okButton = alert.getDialogPane.lookupButton(ButtonType.OK).asInstanceOf[Button]
        okButton.setPrefWidth(60)
        okButton.setText("Ok")
        okButton.setDefaultButton(true)

        alert.showAndWait()
        false
    }
  }

}

object SettingsWindow {

  var instance: SettingsWindow = _

} 
