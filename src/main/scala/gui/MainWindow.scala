package gui

import core.Difficulty.Easy
import core.ProgramState.InMainMenu
import core.{Board, GameState, MyRandom, Settings}
import gui.Program.container
import io.{IOUtils, Serializer}
import javafx.application.Platform
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.control.{Button, Label}
import javafx.scene.layout.{AnchorPane, ColumnConstraints, GridPane, RowConstraints}
import javafx.scene.{Parent, Scene}
import javafx.stage.{Modality, Stage}
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

  val random = MyRandom(IOUtils.loadSeed())
  val boardLen = 7
  val difficulty = Easy
  val defaultSettings = Settings(boardLen, difficulty)

  val initialState = GameState(None, defaultSettings.difficulty, random, None)
  val defaultContainer = Container(initialState, Nil, InMainMenu, defaultSettings)

  container = IOUtils.checkHasContinue() match {
    case true => Serializer.getLastSavedGame(defaultContainer)
    case _ => defaultContainer
  }

  @FXML
  def initialize(): Unit = {
    refreshInfo()
  }

  MainWindow.instance = this

  def refreshInfo(): Unit = {
    updateSettingsLabels(container)
    container.gameState.board match {
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
    container = Container(
      GameState(
        Some(Board.initBoard(container.newGameSettings.boardLength)),
        container.newGameSettings.difficulty,
        container.gameState.random,
        container.gameState.winner,
      ),
      Nil,
      container.programState,
      container.newGameSettings
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
        container = Serializer.getSavedGame(path, container, container.programState)
        startGame(false)
      case None =>
    }

  }

  def btnSettingsOnClicked(): Unit = {
    val fxmlLoader = new FXMLLoader(getClass.getResource("SettingsWindow.fxml"))
    val settingsWindow = fxmlLoader.load().asInstanceOf[GridPane]

    val scene = btnSettings.getScene
    scene.setRoot(settingsWindow)

    settingsWindow.applyCss()
    settingsWindow.layout()
    println(settingsWindow.getWidth)
    println(settingsWindow.getHeight)
    println(settingsWindow.getPrefWidth)
    println(settingsWindow.getPrefHeight)

    Program.setWindowSizeAndCenter(472.0, 492.0)
  }

  def btnQuitOnClicked(): Unit = {
    finalizeAndExit()
  }

  def startGame(newGame: Boolean): Unit = {
    val gameBoard = new GUIBoard(container.gameState.board.get.length)
    if (!newGame) {
      gameBoard.initHexagons(container.gameState.board.get)
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

    val scene = btnStart.getScene
    scene.setRoot(gameWindow)

    val buttonPane: AnchorPane = gameWindow.getChildren.get(1).asInstanceOf[AnchorPane]

    gameWindow.applyCss()
    gameWindow.layout()

    val minWidth = math.max(gameBoard.getWidth, buttonPane.getWidth)
    val minHeight = gameBoard.getHeight + buttonPane.getHeight
    Program.setWindowSizeAndCenter(minWidth, minHeight)


  }

  private def finalizeAndExit(): Unit = {
    IOUtils.saveSeed(container.gameState.random.getSeed())
    Platform.exit()
  }

}

object MainWindow {
  
  var instance: MainWindow = _

  var widthWinExtra: Double = 12.0
  var heightWinExtra: Double = 32.0

}
