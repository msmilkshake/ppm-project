import io.BoardPrinter.printBoard
import io.StringUtils.{blueString, redString}
import logic.Board.Board
import logic.Cells._
import logic.GameLogic.hasContiguousLine

object Tests {

  val b1: Board = List(
    List(Empty, Empty, Empty, Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty)
  )

  val b2: Board = List(
    List(Red, Empty, Empty, Blue, Empty),
    List(Empty, Red, Empty, Blue, Empty),
    List(Empty, Red, Empty, Empty, Blue),
    List(Empty, Empty, Blue, Blue, Empty),
    List(Empty, Empty, Empty, Red, Red)
  )

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
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,Empty, Red),
    List(Empty, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,Red, Empty),
    List(Empty, Empty, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red,Empty, Empty),
    List(Empty, Empty, Empty, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Empty,Empty, Empty),
    List(Empty, Empty, Empty, Empty, Blue, Empty, Empty, Empty, Empty, Empty, Red, Empty, Empty,Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty, Blue, Empty, Empty, Empty, Red, Empty, Empty, Empty,Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty, Empty, Blue, Empty, Red, Empty, Empty, Empty, Empty,Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Empty, Empty, Empty, Empty, Empty,Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty, Empty, Red, Empty, Blue, Empty, Empty, Empty, Empty,Empty, Empty),
    List(Empty, Empty, Empty, Empty, Empty, Red, Empty, Empty, Empty, Blue, Empty, Empty, Empty,Empty, Empty),
    List(Empty, Empty, Empty, Empty, Red, Empty, Empty, Empty, Empty, Empty, Blue, Empty, Empty,Empty, Empty),
    List(Empty, Empty, Empty, Red, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Blue, Empty,Empty, Empty),
    List(Empty, Empty, Red, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Blue,Empty, Empty),
    List(Empty, Red, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,Blue, Empty),
    List(Red, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,Empty, Blue),
  )

  val b5: Board = List(
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red),
    List(Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red, Blue, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Red)
  )

  val a1: Board = List(
    List(Red, Red, Red, Red, Blue),
    List(Red, Empty, Red, Blue, Empty),
    List(Empty, Red, Blue, Red, Empty),
    List(Empty, Red, Blue, Blue, Empty),
    List(Blue, Red, Red, Red, Red)
  )

  val a2: Board = List(
    List(Red, Blue, Red, Blue, Empty),
    List(Blue, Empty, Blue, Empty, Empty),
    List(Blue, Red, Blue, Blue, Empty),
    List(Empty, Red, Empty, Blue, Empty),
    List(Empty, Red, Red, Blue, Red)
  )

  val a3: Board = List(
    List(Red, Red, Red, Red, Blue),
    List(Red, Empty, Red, Blue, Empty),
    List(Empty, Red, Blue, Red, Empty),
    List(Empty, Red, Blue, Blue, Empty),
    List(Blue, Empty, Red, Red, Red)
  )
  
  def main(args: Array[String]): Unit = {
    printBoard(a1)
    println(f"${redString("Red")} wins? ${hasContiguousLine(a1, Red)}")
    println(f"${blueString("Blue")} wins? ${hasContiguousLine(a1, Blue)}")

    printBoard(a2)
    println(f"${redString("Red")} wins? ${hasContiguousLine(a2, Red)}")
    println(f"${blueString("Blue")} wins? ${hasContiguousLine(a2, Blue)}")

    printBoard(a3)
    println(f"${redString("Red")} wins? ${hasContiguousLine(a3, Red)}")
    println(f"${blueString("Blue")} wins? ${hasContiguousLine(a3, Blue)}")
  }
  

}
