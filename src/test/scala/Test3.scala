import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.stage.Stage

class Test3 extends Application {
    override def start(primaryStage: Stage): Unit = {
        val anchorPane = new AnchorPane()

        val pane = new Pane()
        pane.setStyle("-fx-background-color: lightblue;")
        AnchorPane.setTopAnchor(pane, 0.0)
        AnchorPane.setLeftAnchor(pane, 0.0)
        AnchorPane.setRightAnchor(pane, 0.0)
        AnchorPane.setBottomAnchor(pane, 0.0)

        val button = new Button("Button")
        AnchorPane.setTopAnchor(button, 20.0)
        AnchorPane.setLeftAnchor(button, 20.0)

        anchorPane.getChildren.addAll(pane, button)

        primaryStage.setScene(new Scene(anchorPane, 400, 400))
        primaryStage.show()
    }
    
}

object App2 {
    def main(args: Array[String]): Unit = {
        Application.launch(classOf[Test3], args: _*)
    }
}