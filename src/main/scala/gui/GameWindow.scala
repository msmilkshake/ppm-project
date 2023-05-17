package gui

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.Pane

class GameWindow {
    
    @FXML
    var gamePane: Pane = _
    @FXML
    var btnBack: Button = _
    
    def btnBackOnClicked(): Unit = {
        btnBack.getScene.setRoot(Program.mainViewRoot)
        MainWindow.setWindowSizeAndCenter(Program.startWinWidth, Program.startWinHeight)
    }
    
    def setPane(pane: Pane): Unit = {
        gamePane = pane
    }

}

object GameWindow {
    var uiBoard: GUIBoard  = _
}
