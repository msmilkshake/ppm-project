import Core.Board.Board
import Core.Cells.{Blue, Empty, Red}
import IO.BoardPrinter

object BoardTest extends App {

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

  BoardPrinter.printBoard(b3)

}
