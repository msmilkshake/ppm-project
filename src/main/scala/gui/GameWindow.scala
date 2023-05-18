package gui

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.Pane

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
        
    }
    
    GameWindow.instance = this
    
    def btnUndoOnClicked(): Unit = {
        
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
    var instance : GameWindow = _
    var uiBoard: GUIBoard  = _
}
