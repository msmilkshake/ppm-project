package Core

import Core.Board.Board
import Core.Cells.{Cell, Empty}


object GameCore {

  def randomMove (board: Board, rand: MyRandom):((Int, Int),MyRandom) = {
    val validCells = filterCells(board,Empty) //lista celulas vazias validas para jogar
    val (n, nextRnd) = rand.nextInt(validCells.length) // escolher um número entre 0 e o máx da lista (será o indice)
    (validCells(n),nextRnd.asInstanceOf[MyRandom]) //posição aleatória da lista de celulas vazias, nextRandom
  }


  def filterCells (board: Board, cell: Cell): List[(Int,Int)] = {
    board.zipWithIndex.flatMap{
      case (subList, row) => subList.zipWithIndex.collect {
        case (cell1, col) if cell1 equals cell => (row,col)
      }
    }
  }

}
