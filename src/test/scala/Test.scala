import Core.Board.Board
import Core.Cells.{Blue, Empty, Red}
import Core.GameCore.filterCells
import IO.BoardDrawer.drawBoard

object Test {

  def main(args: Array[String]): Unit = {
    def b1: Board = List(List(Red, Empty, Blue), List(Empty, Empty, Empty), List(Empty, Blue, Empty))

    val emptyCells = filterCells(b1, Empty)
    println(emptyCells)

    def b2: Board = List(List(Red, Empty, Blue, Blue), List(Empty, Empty, Empty, Blue), List(Empty, Blue, Empty, Red), List(Empty, Empty, Empty, Empty))

    val b3: Board = List(
      List(Empty, Empty, Empty, Empty, Blue, Empty, Empty, Empty),
      List(Empty, Red, Red, Blue, Empty, Empty, Empty, Empty),
      List(Red, Empty, Blue, Red, Red, Empty, Empty, Empty),
      List(Empty, Blue, Empty, Empty, Red, Red, Empty, Empty),
      List(Empty, Blue, Blue, Empty, Empty, Red, Red, Empty),
      List(Empty, Empty, Blue, Empty, Empty, Empty, Red, Red),
      List(Empty, Blue, Empty, Empty, Empty, Empty, Empty, Empty),
      List(Empty, Blue, Empty, Empty, Empty, Empty, Empty, Empty),
    )

    val b4: Board = List(
      List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
      List(Empty, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Empty),
      List(Empty, Empty, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Empty, Empty),
      List(Empty, Empty, Empty, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Empty, Empty, Empty),
      List(Empty, Empty, Empty, Empty, Blue, Empty, Empty, Empty, Empty, Empty, Red, Empty, Empty, Empty, Empty),
      List(Empty, Empty, Empty, Empty, Empty, Blue, Empty, Empty, Empty, Red, Empty, Empty, Empty, Empty, Empty),
      List(Empty, Empty, Empty, Empty, Empty, Empty, Blue, Empty, Red, Empty, Empty, Empty, Empty, Empty, Empty),
      List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
      List(Empty, Empty, Empty, Empty, Empty, Empty, Red, Empty, Blue, Empty, Empty, Empty, Empty, Empty, Empty),
      List(Empty, Empty, Empty, Empty, Empty, Red, Empty, Empty, Empty, Blue, Empty, Empty, Empty, Empty, Empty),
      List(Empty, Empty, Empty, Empty, Red, Empty, Empty, Empty, Empty, Empty, Blue, Empty, Empty, Empty, Empty),
      List(Empty, Empty, Empty, Red, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Blue, Empty, Empty, Empty),
      List(Empty, Empty, Red, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Blue, Empty, Empty),
      List(Empty, Red, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Blue, Empty),
      List(Red, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Blue),
    )

  }

}
