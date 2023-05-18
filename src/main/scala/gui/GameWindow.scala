package gui

import core.Cells.Empty
import core.{GameLogic, GameState}
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tui.Container

import java.net.URL
import java.util.ResourceBundle

class GameWindow {

  @FXML
  var gamePane: Pane = _
  @FXML
  var btnBack: Button = _
  @FXML
  var btnUndo: Button = _

  @FXML
  def initialize(url: URL, rb: ResourceBundle): Unit = {
    println()
  }

  GameWindow.instance = this

  def btnUndoOnClicked(): Unit = {
    val size = MainWindow.c.gameState.board.get.length
    MainWindow.c.playHistory match {
      case Nil =>
      case (cRow, cCol) :: (pRow, pCol) :: tail =>
        GameWindow.uiBoard.grid(size - cRow - 1)(size - cCol - 1).setFill(Color.LIGHTGRAY)
        GameWindow.uiBoard.grid(size - pRow - 1)(size - pCol- 1).setFill(Color.LIGHTGRAY)
        val b1 = GameLogic.setBoardCell(MainWindow.c.gameState.board.get, Empty, pRow, pCol)
        MainWindow.c = Container(
          GameState(
            Some(GameLogic.setBoardCell(b1, Empty, cRow, cCol)),
            MainWindow.c.gameState.difficulty,
            MainWindow.c.gameState.random,
            MainWindow.c.gameState.winner),
          tail,
          MainWindow.c.programState,
          MainWindow.c.newGameSettings
        )
    }
  }

  def btnBackOnClicked(): Unit = {
    btnBack.getScene.setRoot(Program.mainViewRoot)
    MainWindow.instance.refreshInfo()
    MainWindow.setWindowSizeAndCenter(Program.startWinWidth, Program.startWinHeight)
  }

  def setPane(pane: Pane): Unit = {
    gamePane = pane
  }

}

object GameWindow {
  var instance: GameWindow = _
  var uiBoard: GUIBoard = _
}
