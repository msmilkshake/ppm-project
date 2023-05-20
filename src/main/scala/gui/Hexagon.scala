package gui;

import core.Board.Board
import core.Cells.{Blue, Empty, Red}
import core.{GameLogic, GameState}
import gui.Program.container
import javafx.scene.paint.Color
import javafx.scene.shape.Polygon
import tui.Container;

case class Hexagon(x: Double, y: Double, side: Double) extends Polygon {

  private def updateVertices(): Unit = Hexagon.updateVertices(this)

  def configureButton(size: Int, row: Int, col: Int): Unit =
    Hexagon.configureButton(this, size, row, col)

  setLayoutX(x)
  setLayoutY(y)
  updateVertices()

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

  def configureButton(hex: Hexagon, size: Int, pRow: Int, pCol: Int): Unit = {
    hex.setOnMouseClicked(_ => {
      val s: GameState = container.gameState

      if (s.board.get(pRow)(pCol) == Empty) {
        val b1: Board = GameLogic.setBoardCell(s.board.get, Red, pRow, pCol)
        hex.setFill(Color.RED)

        if (GameLogic.hasContiguousLine(b1, Red)) {
          container = Container(
            GameState(
              None,
              container.newGameSettings.difficulty,
              container.gameState.random,
              Some(Red)),
            Nil,
            container.programState,
            container.newGameSettings)
          GameWindow.instance.gameWon()
        } else {
          val gs1 = GameState(
            Some(b1),
            s.difficulty,
            s.random,
            None)

          val (Some((cRow, cCol)), _, rand) = GameLogic.doMove(gs1, GameLogic.computerMove(gs1))
          val b2 = GameLogic.setBoardCell(gs1.board.get, Blue, cRow, cCol)
          val gs2 = GameState(
            Some(b2),
            s.difficulty,
            rand,
            None)

          GameWindow.uiBoard.grid(size - cRow - 1)(size - cCol - 1).setFill(Color.DODGERBLUE)

          if (GameLogic.hasContiguousLine(gs2.board.get, Blue)) {
            container = Container(
              GameState(
                None,
                container.newGameSettings.difficulty,
                container.gameState.random,
                Some(Blue)),
              Nil,
              container.programState,
              container.newGameSettings)
            GameWindow.instance.gameWon()
          }

          val c: Container = Container(gs2,
            (cRow, cCol) :: (pRow, pCol) :: container.playHistory,
            container.programState,
            container.newGameSettings
          )
          container = c
        }
      }
    })

    hex.setOnMouseEntered(_ =>
      hex.getFill.asInstanceOf[Color] match {
        case Color.LIGHTGRAY =>
          hex.setFill(Color.LIGHTCORAL)
        case _ =>
      }
    )

    hex.setOnMouseExited(_ =>
      hex.getFill.asInstanceOf[Color] match {
        case Color.LIGHTCORAL =>
          hex.setFill(Color.LIGHTGRAY)
        case _ =>
      }
    )
  }

}
