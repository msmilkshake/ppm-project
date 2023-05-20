package gui

import gui.Program.container
import io.Serializer
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.{Screen, Stage}
import tui.Container

class Program extends Application {

  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Hex Game")
    Program.primaryStage = primaryStage
    val fxmlLoader = new FXMLLoader(getClass.getResource("MainMenuWindow.fxml"))
    Program.mainViewRoot = fxmlLoader.load()
    val scene = new Scene(Program.mainViewRoot)
    primaryStage.setScene(scene)
    primaryStage.show()
    Program.mainViewRoot.applyCss()
    Program.mainViewRoot.layout()
    Program.startWinWidth = scene.getWidth
    Program.startWinHeight = scene.getHeight
    Program.setWindowSizeAndCenter(Program.startWinWidth, Program.startWinHeight)
  }

  override def stop(): Unit = {
    if (container.gameState.board.nonEmpty) {
      Serializer.saveGameAuto(container)
    }
  }

}

object Program {

  var startWinWidth: Double = _
  var startWinHeight: Double = _

  var primaryStage: Stage = _
  var mainViewRoot: Parent = _

  var container: Container = _

  def setWindowSizeAndCenter(width: Double, height: Double): Unit = {
    Program.primaryStage.setMinWidth(width + MainMenuWindow.widthWinExtra)
    Program.primaryStage.setWidth(width + MainMenuWindow.widthWinExtra)
    Program.primaryStage.setMinHeight(height + MainMenuWindow.heightWinExtra)
    Program.primaryStage.setHeight(height + MainMenuWindow.heightWinExtra)

    val screenBounds = Screen.getPrimary.getVisualBounds
    val centerX = screenBounds.getMinX + (screenBounds.getWidth / 2)
    val centerY = screenBounds.getMinY + (screenBounds.getHeight / 2)

    Program.primaryStage.setX(centerX - (width / 2))
    Program.primaryStage.setY(centerY - (height / 2))
  }
    
}