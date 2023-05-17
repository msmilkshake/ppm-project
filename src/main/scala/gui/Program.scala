package gui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.layout.GridPane
import javafx.scene.{Parent, Scene}
import javafx.stage.{Screen, Stage}

class Program extends Application {

    override def start(primaryStage: Stage): Unit = {
        primaryStage.setTitle("Hex Game")
        Program.primaryStage = primaryStage
        val fxmlLoader = new FXMLLoader(getClass.getResource("MainWindow.fxml"))

        Program.mainViewRoot = fxmlLoader.load()
        val scene = new Scene(Program.mainViewRoot)
        primaryStage.setScene(scene)
        MainWindow.setWindowSizeAndCenter(Program.startWinWidth, Program.startWinHeight)
        primaryStage.show()

    }

}

object Program {
    val startWinWidth = 400
    val startWinHeight = 440
    
    var primaryStage: Stage = _
    var mainViewRoot: Parent = _
}