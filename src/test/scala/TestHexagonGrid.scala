import gui.Hexagon
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.{Polygon, StrokeLineCap, StrokeLineJoin}
import javafx.stage.Stage

import scala.math

class TestHexagonGrid extends Application {
    override def start(stage: Stage): Unit = {

        val rows = 10
        val cols = rows
        val length = 20
        val h = length * math.sqrt(3) / 2
        val hMult = (rows + 1) / 2 * 2
        val lenMult = rows / 2
        val width = cols * 2 * h + (cols - 1) * h + 100
        val height = hMult * length + lenMult * length + ((rows + 1) % 2 * (h / 2)) + 100
        println(height)
        val root: Pane = createHoneycombGrid(rows, cols, length)
        val scene: Scene = new Scene(root, width, height)
        println(root.getLayoutBounds)
        root.setMinWidth(width)
        root.setPrefWidth(width)
        root.setMinHeight(height)
        root.setPrefHeight(height)
        stage.setTitle("Hexagon Grid Demo")
        stage.setScene(scene)
        stage.show()
        stage.setMinWidth(stage.getWidth)
        stage.setMinHeight(stage.getHeight)
        println(stage.getScene.getHeight)
    }

    private def createHoneycombGrid(rows: Int, cols: Int, length: Double): Pane = {
        val pane = new Pane

        val border = 50

        val h = length * math.sqrt(3) / 2

        var xOffset = h + border
        var yOffset = length + border

        val hMult = (rows + 1) / 2 * 2
        val lenMult = rows / 2
        val width = cols * 2 * h + (cols - 1) * h
        val height = hMult * length + lenMult * length + ((rows + 1) % 2 * (h / 2))

        val bg1 = new Polygon()
        bg1.getPoints.addAll(
            -h / 2 + border, length / 4 + border,
            width / 2 + border, height / 2 + border,
            (rows - 1) * h + border, height + length / 2 + border,
            -h + border, length + border
        )
        bg1.setFill(Color.RED)
        bg1.setStroke(Color.BLACK)
        bg1.setStrokeWidth(3.0)
        bg1.setStrokeLineCap(StrokeLineCap.ROUND)
        bg1.setStrokeLineJoin(StrokeLineJoin.ROUND)
        pane.getChildren.add(bg1)

        val bg2 = new Polygon()
        bg2.getPoints.addAll(
            -h / 2 + border, length / 4 + border,
            border, -length / 2 + border,
            width - (rows - 1) * h  + border, -length / 2 + border,
            width / 2 + border, height / 2 + border
        )
        bg2.setFill(Color.DODGERBLUE)
        bg2.setStrokeWidth(3.0)
        bg2.setStrokeLineCap(StrokeLineCap.ROUND)
        bg2.setStrokeLineJoin(StrokeLineJoin.ROUND)
        bg2.setStroke(Color.BLACK)
        pane.getChildren.add(bg2)

        val bg3 = new Polygon()
        bg3.getPoints.addAll(
            width + h / 2 + border, height - length / 4 + border,
            width / 2 + border, height / 2 + border,
            width - (rows - 1) * h  + border, -length / 2 + border,
            width + h + border, height - length + border
        )
        
        bg3.setFill(Color.RED)
        bg3.setStroke(Color.BLACK)
        bg3.setStrokeWidth(3.0)
        bg3.setStrokeLineCap(StrokeLineCap.ROUND)
        bg3.setStrokeLineJoin(StrokeLineJoin.ROUND)
        pane.getChildren.add(bg3)

        val bg4 = new Polygon()
        bg4.getPoints.addAll(
            width + h / 2 + border, height - length / 4 + border,
            width + border, height + length / 2 + border,
            (rows - 1) * h + border, height + length / 2 + border,
            width / 2 + border, height / 2 + border
        )
        bg4.setFill(Color.DODGERBLUE)
        bg4.setStroke(Color.BLACK)
        bg4.setStrokeWidth(3.0)
        bg4.setStrokeLineCap(StrokeLineCap.ROUND)
        bg4.setStrokeLineJoin(StrokeLineJoin.ROUND)
        pane.getChildren.add(bg4)

        for (i <- 0 until rows) {
            xOffset = h + h * i + border
            for (j <- 0 until cols) {
                val hexagon = Hexagon(xOffset, yOffset, length)
                hexagon.setFill(Color.LIGHTGRAY)
                hexagon.setStroke(Color.BLACK)
                hexagon.setStrokeWidth(3.0)
                if (rows % 2 == 1 && i == rows / 2 && j == rows / 2) {
//                    hexagon.setVisible(false)
                } else if (rows % 2 == 0) {
                    if ((i == rows / 2 - 1 && j == rows / 2 - 1) ||
                        (i == rows / 2 - 1 && j == rows / 2) ||
                        (i == rows / 2 && j == rows / 2 - 1) ||
                        (i == rows / 2 && j == rows / 2)) {
//                        hexagon.setVisible(false)
                    }
                }
//                hexagon.setVisible(false)
                pane.getChildren.add(hexagon)
                xOffset += h * 2
            }
            yOffset += length + length / 2
        }
        pane
    }

}

object App {
    def main(args: Array[String]): Unit = {
        Application.launch(classOf[TestHexagonGrid], args: _*)
    }
}
