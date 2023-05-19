package gui

import core.Difficulty.{Easy, Medium}
import core.{GameState, Settings}
import io.IOUtils
import javafx.fxml.FXML
import javafx.scene.control._
import tui.Container

import scala.util.{Failure, Success, Try}

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
              txtBoardLength.setText(f"${MainWindow.c.newGameSettings.boardLength}")
            case _ =>
          }
      })
  }

  def refreshInfo(): Unit = {
    MainWindow.c.newGameSettings.difficulty match {
      case Easy =>
        rbEasy.fire()
      case Medium =>
        rbMedium.fire()
    }
    txtBoardLength.setText(f"${MainWindow.c.newGameSettings.boardLength}")
  }

  def btnEraseAutoSaveOnClicked(): Unit = {
    val confirmation: Alert = new Alert(Alert.AlertType.CONFIRMATION)
    confirmation.setTitle("Confirmation")
    confirmation.setHeaderText("Are you sure you want to delete the autosave?")
    confirmation.showAndWait().get() match {
      case ButtonType.OK =>
        IOUtils.deleteFileQuiet()
        MainWindow.c = Container(
          GameState(
            None,
            MainWindow.c.newGameSettings.difficulty,
            MainWindow.c.gameState.random,
            None),
          Nil,
          MainWindow.c.programState,
          MainWindow.c.newGameSettings
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
    MainWindow.setWindowSizeAndCenter(Program.startWinWidth, Program.startWinHeight)
  }

  def validateSettings(): Boolean = {
    Try(txtBoardLength.getText.toInt) match {
      case Success(value) =>
        MainWindow.c = Container(
          MainWindow.c.gameState,
          MainWindow.c.playHistory,
          MainWindow.c.programState,
          Settings(value,
            rbEasy.isSelected match {
              case true => Easy
              case _ => Medium
            })
        )
        true

      case Failure(_) =>
        val alert = new Alert(Alert.AlertType.INFORMATION)
        alert.setTitle("Invalid Board Length")
        alert.setHeaderText("The length of the board must be between 4 and 25.")

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
