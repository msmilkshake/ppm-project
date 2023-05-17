package gui;

import javafx.scene.shape.Polygon;

case class Hexagon(x: Double, y: Double, side: Double) extends Polygon {
    
    var id: String = _

    private def updateVertices(): Unit = Hexagon.updateVertices(this)

    setLayoutX(x)
    setLayoutY(y)
    updateVertices()
    
    def setIdenifier(id: String): Unit = {
        this.id = id
    }
}

object Hexagon {

    def updateVertices(hex: Hexagon): Unit = {
        val h = hex.side * Math.sqrt(3) / 2

        hex.getPoints.clear() // Clear points before adding new ones

        hex.getPoints.addAll(
            0.0, -hex.side,
            h, -hex.side / 2,
            h, hex.side / 2,
            0.0, hex.side,
            -h, hex.side / 2,
            -h, -hex.side / 2
        )
    }

    def getSide(hex: Hexagon): Double = hex.side
}