import Tests.b3
import core.Board.Board
import core.MyRandom

object TestRandom {
  def main(args: Array[String]): Unit = {
    def rec(board: Board, len: Int): Unit = {
      len match {
        case 0 =>
        case _ =>
          val n = board.length - len
          println(n)
          rec(board, len - 1)
      }
    }
    
    rec(b3, b3.length)
  }
}
