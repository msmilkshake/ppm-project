import io.BoardPrinter
import io.BoardPrinter.{printBoard, redString}
import logic.Cells._

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
  
  def main(args: Array[String]): Unit = {
    printBoard(b3)
    println("\n")
    printBoard(b4)
  }

}
