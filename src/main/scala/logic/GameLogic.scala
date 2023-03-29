package logic

import logic.Cells.Board

import scala.annotation.tailrec

case class GameLogic() {
  def randomMove(board: Board, rand: MyRandom): ((Int, Int), MyRandom) =
    GameLogic.randomMove(board, rand)
  
}

object GameLogic {
  @tailrec
  def randomMove(board: Board, rand: MyRandom): ((Int, Int), MyRandom) = {
    val (row, newRand1) = rand.nextInt(board.length)
    val (col, newRand2) = newRand1.nextInt(board.length)
    board(row)(col) match {
      case logic.Cells.Empty => ((row, col), newRand2.asInstanceOf[MyRandom])
      case _ => randomMove(board, newRand2.asInstanceOf[MyRandom])
    }
  }
}
