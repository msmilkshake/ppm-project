package gui

import core.Difficulty.Easy
import core.ProgramState.InMainMenu
import core.{Board, GameState, MyRandom, Settings}
import io.{IOUtils, Serializer}
import javafx.application.Platform
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{Button, Label}
import javafx.scene.layout.{ColumnConstraints, GridPane, RowConstraints}
import javafx.scene.{Parent, Scene}
import javafx.stage.{Modality, Screen, Stage}
import tui.Container

class MainWindow {

  @FXML
  private var btnStart: Button = _
  @FXML
  private var btnContinue: Button = _
  @FXML
  private var btnLoad: Button = _
  @FXML
  private var btnSettings: Button = _
  @FXML
  private var btnQuit: Button = _
  @FXML
  private var lblLength: Label = _
  @FXML
  private var lblDifficulty: Label = _

  val random = MyRandom(0x54321)
  val boardLen = 4
  val difficulty = Easy
  val defaultSettings = Settings(boardLen, difficulty)

  val initialState = GameState(None, defaultSettings.difficulty, random, None)
  val defaultContainer = Container(initialState, Nil, InMainMenu, defaultSettings)

  MainWindow.c = IOUtils.checkHasContinue() match {
    case true => Serializer.getLastSavedGame(defaultContainer)
    case _ => defaultContainer
  }

  @FXML
  def initialize(): Unit = {
    refreshInfo()
  }

  MainWindow.instance = this

  def refreshInfo(): Unit = {
    updateSettingsLabels(MainWindow.c)
    MainWindow.c.gameState.board match {
      case Some(_) =>
        btnContinue.setVisible(true)
      case None =>
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
        MainWindow.c.newGameSettings.difficulty,
        MainWindow.c.gameState.random,
        MainWindow.c.gameState.winner,
      ),
      Nil,
      MainWindow.c.programState,
      MainWindow.c.newGameSettings
    )
    startGame(true)
  }

  def btnContinueOnClicked(): Unit = {
    startGame(false)
  }

  def btnLoadOnClicked(): Unit = {
    val popupStage: Stage = new Stage()
    val fxmlLoader = new FXMLLoader(getClass.getResource("LoadPopup.fxml"))
    val ViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(ViewRoot)
    popupStage.setScene(scene)
    popupStage.initModality(Modality.WINDOW_MODAL)
    popupStage.initOwner(btnLoad.getScene.getWindow)
    popupStage.showAndWait()
    LoadPopup.instance.filename match {
      case Some(filename) => 
        val path = f"${IOUtils.saveFolderPath}$filename"
        MainWindow.c = Serializer.getSavedGame(path, MainWindow.c, MainWindow.c.programState)
        startGame(false)
      case None =>
    }
    
  }

  def btnSettingsOnClicked(): Unit = {
    val fxmlLoader = new FXMLLoader(getClass.getResource("SettingsWindow.fxml"))
    val settingsWindow = fxmlLoader.load().asInstanceOf[GridPane]

    val scene = btnSettings.getScene
    scene.setRoot(settingsWindow)
    MainWindow.setWindowSizeAndCenter(settingsWindow.getWidth, settingsWindow.getHeight)
  }

  def btnQuitOnClicked(): Unit = {
    finalizeAndExit()
  }

  def startGame(newGame: Boolean): Unit = {
    val gameBoard = new GUIBoard(MainWindow.c.gameState.board.get.length)
    if (!newGame) {
      gameBoard.initHexagons(MainWindow.c.gameState.board.get)
    }
    gameBoard.setId("gamePane")
    GameWindow.uiBoard = gameBoard

    val fxmlLoader = new FXMLLoader(getClass.getResource("GameWindow.fxml"))
    val gameWindow = fxmlLoader.load().asInstanceOf[GridPane]

    val gamePane: GridPane = gameWindow.getChildren.get(0).asInstanceOf[GridPane]
    gamePane.add(gameBoard, 1, 1)

    val rowConst: RowConstraints = gamePane.getRowConstraints.get(1)
    rowConst.setMinHeight(gameBoard.getHeight)
    rowConst.setPrefHeight(gameBoard.getHeight)
    rowConst.setMaxHeight(gameBoard.getHeight)
    val colConst: ColumnConstraints = gamePane.getColumnConstraints.get(1)
    colConst.setMinWidth(gameBoard.getWidth)
    colConst.setPrefWidth(gameBoard.getWidth)
    colConst.setMaxWidth(gameBoard.getWidth)

    val minWidth = gameBoard.getWidth
    val minHeight = gameBoard.getHeight + 40
    MainWindow.setWindowSizeAndCenter(minWidth, minHeight)

    val scene = btnStart.getScene
    scene.setRoot(gameWindow)
  }

  private def finalizeAndExit(): Unit = {
    Platform.exit()
  }

}

object MainWindow {
  var instance: MainWindow = _
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
