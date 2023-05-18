package gui

import core.Board.Board
import core.Cells.{Blue, Empty, Red}
import core.{GameLogic, GameState}
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.{Polygon, StrokeLineCap, StrokeLineJoin}
import tui.Container

import scala.annotation.tailrec

case class GUIBoard(n: Int) extends Pane() {
  
  val h = GUIBoard.l * math.sqrt(3) / 2

  val widthMultiplier = (n + 1) / 2 * 2
  val heightMultiplier = n / 2

  val width = n * 2 * h + (n - 1) * h
  val height = widthMultiplier * GUIBoard.l + heightMultiplier *
    GUIBoard.l + ((n + 1) % 2 * (h / 2))

  setMinWidth(width + 2 * GUIBoard.border)
  setWidth(width + 2 * GUIBoard.border)
  setMinHeight(height + 2 * GUIBoard.border)
  setHeight(height + 2 * GUIBoard.border)

  var xOffset = h + GUIBoard.border
  var yOffset = GUIBoard.l + GUIBoard.border

  val p1 = (-h / 2 + GUIBoard.border, GUIBoard.l / 4 + GUIBoard.border)
  val p2 = (GUIBoard.border, -GUIBoard.l / 2 + GUIBoard.border)
  val p3 = (width - (n - 1) * h + GUIBoard.border, -GUIBoard.l / 2 + GUIBoard.border)
  val p4 = (width / 2 + GUIBoard.border, height / 2 + GUIBoard.border)
  val p5 = ((n - 1) * h + GUIBoard.border, height + GUIBoard.l / 2 + GUIBoard.border)
  val p6 = (-h + GUIBoard.border, GUIBoard.l + GUIBoard.border)
  val p7 = (width + h + GUIBoard.border, height - GUIBoard.l + GUIBoard.border)
  val p8 = (width + h / 2 + GUIBoard.border, height - GUIBoard.l / 4 + GUIBoard.border)
  val p9 = (width + GUIBoard.border, height + GUIBoard.l / 2 + GUIBoard.border)

  GUIBoard.createMargins(this)

  val grid: GUIBoard.HexGrid = GUIBoard.createGrid(this, n)
  
  def initHexagons(b: Board) = GUIBoard.initHexagons(this, b)

}

object GUIBoard {

  type HexGrid = List[List[Hexagon]]

  val border = 50.0
  val l = 20.0

  def createMargins(b: GUIBoard): Unit = {
    createMarginPolygon(b, Color.DODGERBLUE, List(b.p1, b.p2, b.p3, b.p4))
    createMarginPolygon(b, Color.RED, List(b.p4, b.p3, b.p7, b.p8))
    createMarginPolygon(b, Color.DODGERBLUE, List(b.p4, b.p8, b.p9, b.p5))
    createMarginPolygon(b, Color.RED, List(b.p1, b.p4, b.p5, b.p6))
  }

  def createMarginPolygon(b: GUIBoard, color: Color, points: List[(Double, Double)]): Unit = {
    val p = new Polygon()
    p.getPoints.addAll(
      points(0)._1, points(0)._2,
      points(1)._1, points(1)._2,
      points(2)._1, points(2)._2,
      points(3)._1, points(3)._2,
    )
    p.setFill(color)
    p.setStroke(Color.BLACK)
    p.setStrokeLineCap(StrokeLineCap.ROUND)
    p.setStrokeLineJoin(StrokeLineJoin.ROUND)
    p.setStrokeWidth(3.0)
    b.getChildren.add(p)
  }

  def createGrid(b: GUIBoard, size: Int): HexGrid = {

    @tailrec
    def intiRow(row: List[Hexagon], i: Int, j: Int): List[Hexagon] = {
      j match {
        case 0 =>
          b.yOffset += l + l / 2
          row
        case _ =>
          val hexagon = Hexagon(b.xOffset, b.yOffset, GUIBoard.l)
          hexagon.setFill(Color.LIGHTGRAY)
          hexagon.setStroke(Color.BLACK)
          hexagon.setStrokeLineCap(StrokeLineCap.ROUND)
          hexagon.setStrokeLineJoin(StrokeLineJoin.ROUND)
          hexagon.setStrokeWidth(3.0)
          hexagon.setOnMouseClicked(_ => {
            val s: GameState = MainWindow.c.gameState
            if (s.board.get(i - 1)(j - 1) == Empty) {
              val b1: Board = GameLogic.setBoardCell(s.board.get, Red, i - 1, j - 1)
              hexagon.setFill(Color.RED)
              if (GameLogic.hasContiguousLine(b1, Red)) {
                println("PLAYER WINNER!")
              }
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
              GameWindow.uiBoard.grid(cRow)(cCol).setFill(Color.DODGERBLUE)
              if (GameLogic.hasContiguousLine(gs2.board.get, Blue)) {
                println("COMPUTER WINNER!")
              }
              val c: Container = Container(gs2,
                (cRow,cCol)::(i-1,j-1):: MainWindow.c.playHistory,
                MainWindow.c.programState,
                MainWindow.c.newGameSettings
              )
              MainWindow.c = c
            }
          })
          b.getChildren.add(hexagon)
          b.xOffset += b.h * 2
          intiRow(hexagon :: row, i, j - 1)
      }
    }

    @tailrec
    def intiRows(grid: HexGrid, i: Int): HexGrid = {
      i match {
        case 0 => grid
        case _ =>
          b.xOffset = b.h + b.h * (size - i) + border
          intiRows(intiRow(Nil, i, size) :: grid, i - 1)
      }
    }

    intiRows(Nil, size)
  }


  def initHexagons(instance: GUIBoard, board: Board) = {
    board.zipWithIndex map {
      case (cells, i) => cells.zipWithIndex map {
        case (cell, j) =>
          cell match {
            case Red => instance.grid(i)(j).setFill(Color.RED)
            case Blue => instance.grid(i)(j).setFill(Color.DODGERBLUE)
            case _ =>
          }
      }
    }
  }


}
