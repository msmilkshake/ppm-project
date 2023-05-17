package gui

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class Test extends Application {

    override def start(stage: Stage): Unit = {
        val root: Pane = GUIBoard(10)
        val scene: Scene = new Scene(root, root.getMinWidth, root.getMinHeight)
        println(root.getLayoutBounds)
        stage.setTitle("Hexagon Grid Demo")
        stage.setScene(scene)
        stage.show()
        stage.setMinWidth(stage.getWidth)
        stage.setMinHeight(stage.getHeight)
    }

}

object TestApp {
    def main(args: Array[String]): Unit = {
        Application.launch(classOf[Test], args: _*)
    }
}
