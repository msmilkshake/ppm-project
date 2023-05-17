package gui

import core.Difficulty.{Easy, Medium}
import core.ProgramState.InMainMenu
import core.{Board, GameState, MyRandom, Settings}
import io.{IOUtils, Serializer}
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.{Button, Label, TextField}
import javafx.scene.layout.{ColumnConstraints, GridPane, Pane, Region, RowConstraints}
import javafx.stage.Screen
import tui.Container

import java.net.URL
import java.util.ResourceBundle

class MainWindow extends Initializable {

  @FXML
  private var btnStart: Button = _
  @FXML
  private var btnContinue: Button = _
  @FXML
  private var btnSettings: Button = _
  @FXML
  private var btnQuit: Button = _
  @FXML
  private var lblLength: Label = _
  @FXML
  private var lblDifficulty: Label = _

  val random = MyRandom(0x54321)
  val boardLen = 25
  val difficulty = Medium
  val initialState = GameState(None, difficulty, random, None)
  val defaultSettings = Settings(boardLen, difficulty)
  val defaultContainer = Container(initialState, Nil, InMainMenu, defaultSettings)

  MainWindow.c = IOUtils.checkHasContinue() match {
    case true => Serializer.getLastSavedGame(defaultContainer)
    case _ => defaultContainer
  }

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    updateSettingsLabels(MainWindow.c)
    if (!IOUtils.checkHasContinue()) {
      btnContinue.setVisible(false)
    }
  }

  def updateSettingsLabels(c: Container): Unit = {
    lblLength.setText(c.newGameSettings.boardLength.toString)
    lblDifficulty.setText(if (c.newGameSettings.difficulty == Easy) "Easy" else "Medium")
  }


  def btnPlayOnClicked(): Unit = {
    MainWindow.c = Container(
      GameState(
        Some(Board.initBoard(MainWindow.c.newGameSettings.boardLength)),
        MainWindow.c.gameState.difficulty,
        MainWindow.c.gameState.random,
        MainWindow.c.gameState.winner,
      ),
      MainWindow.c.stateHistory,
      MainWindow.c.programState,
      MainWindow.c.newGameSettings
    )
    val gameBoard = new GUIBoard(MainWindow.c.newGameSettings.boardLength)
    gameBoard.setId("gamePane")
    GameWindow.uiBoard = gameBoard

    val fxmlLoader = new FXMLLoader(getClass.getResource("GameWindow.fxml"))
    val gameWindow = fxmlLoader.load().asInstanceOf[GridPane]
    
    val gamePane: GridPane = gameWindow.getChildren.get(0).asInstanceOf[GridPane]
    gamePane.add(gameBoard, 1, 1)

    val rowConst: RowConstraints =  gamePane.getRowConstraints.get(1)
    rowConst.setMinHeight(gameBoard.getHeight)
    rowConst.setPrefHeight(gameBoard.getHeight)
    rowConst.setMaxHeight(gameBoard.getHeight)
    val colConst: ColumnConstraints =  gamePane.getColumnConstraints.get(1)
    colConst.setMinWidth(gameBoard.getWidth)
    colConst.setPrefWidth(gameBoard.getWidth)
    colConst.setMaxWidth(gameBoard.getWidth)
    
    val minWidth = gameBoard.getWidth
    val minHeight = gameBoard.getHeight + 40
    MainWindow.setWindowSizeAndCenter(minWidth, minHeight)

    val scene = btnStart.getScene
    scene.setRoot(gameWindow)

  }

  def btnContinueOnClicked(): Unit = {

  }

  def btnSettingsOnClicked(): Unit = {

  }

  def btnQuitOnClicked(): Unit = {

  }


}

object MainWindow {
  var c: Container = _

  var widthWinExtra: Double = 12.0
  var heightWinExtra: Double = 32.0

  def setWindowSizeAndCenter(width: Double, height: Double): Unit = {

    Program.primaryStage.setMinWidth(width + MainWindow.widthWinExtra)
    Program.primaryStage.setWidth(width + MainWindow.widthWinExtra)
    Program.primaryStage.setMinHeight(height + MainWindow.heightWinExtra)
    Program.primaryStage.setHeight(height + MainWindow.heightWinExtra)

    val screenBounds = Screen.getPrimary.getVisualBounds
    val centerX = screenBounds.getMinX + (screenBounds.getWidth / 2)
    val centerY = screenBounds.getMinY + (screenBounds.getHeight / 2)

    Program.primaryStage.setX(centerX - (width / 2))
    Program.primaryStage.setY(centerY - (height / 2))

  }

}
