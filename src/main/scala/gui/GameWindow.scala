package gui

import core.Cells.Empty
import core.{GameLogic, GameState}
import gui.Program.container
import io.IOUtils
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.scene.{Parent, Scene}
import javafx.stage.{Modality, Stage}
import tui.Container

import java.net.URL
import java.util.ResourceBundle

class GameWindow {

  @FXML
  var btnUndo: Button = _
  @FXML
  var btnSave: Button = _
  @FXML
  var btnBack: Button = _

  @FXML
  def initialize(url: URL, rb: ResourceBundle): Unit = {
  }

  GameWindow.instance = this

  def btnUndoOnClicked(): Unit = {
    val size = container.gameState.board.get.length
    container.playHistory match {
      case Nil =>
      case (cRow, cCol) :: (pRow, pCol) :: tail =>
        GameWindow.uiBoard.grid(size - cRow - 1)(size - cCol - 1).setFill(Color.LIGHTGRAY)
        GameWindow.uiBoard.grid(size - pRow - 1)(size - pCol - 1).setFill(Color.LIGHTGRAY)
        val b1 = GameLogic.setBoardCell(container.gameState.board.get, Empty, pRow, pCol)
        container = Container(
          GameState(
            Some(GameLogic.setBoardCell(b1, Empty, cRow, cCol)),
            container.gameState.difficulty,
            container.gameState.random,
            container.gameState.winner),
          tail,
          container.programState,
          container.newGameSettings
        )
    }
  }

  def btnSaveOnClicked(): Unit = {
    val popupStage: Stage = new Stage()
    val fxmlLoader = new FXMLLoader(getClass.getResource("SavePopup.fxml"))
    val ViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(ViewRoot)
    popupStage.setScene(scene)
    popupStage.initModality(Modality.WINDOW_MODAL)
    popupStage.initOwner(btnSave.getScene.getWindow)
    popupStage.showAndWait()
  }

  def btnBackOnClicked(): Unit = {
    btnBack.getScene.setRoot(Program.mainViewRoot)
    MainWindow.instance.refreshInfo()
    Program.setWindowSizeAndCenter(Program.startWinWidth, Program.startWinHeight)
  }

  def gameWon(): Unit = {
    val popupStage: Stage = new Stage()
    val fxmlLoader = new FXMLLoader(getClass.getResource("GameWonPopup.fxml"))
    val ViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(ViewRoot)
    popupStage.setScene(scene)
    popupStage.initModality(Modality.WINDOW_MODAL)
    popupStage.initOwner(btnUndo.getScene.getWindow)
    GameWonPopup.instance.setStage(popupStage)
    popupStage.showAndWait()
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
    btnBackOnClicked()
  }

}

object GameWindow {

  var instance: GameWindow = _
  var uiBoard: GUIBoard = _

}
