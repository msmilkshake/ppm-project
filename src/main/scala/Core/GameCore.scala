package Core

import Core.Board.Board
import Core.Cells.{Cell, Empty}


object GameCore {

  def randomMove (board: Board, rand: MyRandom):((Int, Int),MyRandom) = {
    val validCells = filterCells(board,Empty) //celulas vazias validas para jogar
    val (n, nextRnd) = rand.nextInt // primeira opção do MyRandom.nextInt
    (validCells(n),nextRnd.asInstanceOf[MyRandom])
  }


  def filterCells (board: Board, cell: Cell): List[(Int,Int)] = {
    board.zipWithIndex.flatMap{
      case (subList, row) => subList.zipWithIndex.collect {
        case (cell1, col) if cell1 equals cell => (row,col)
      }
    }
  }

}
