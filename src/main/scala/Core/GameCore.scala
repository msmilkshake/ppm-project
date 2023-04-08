package Core

import Core.Board.Board
import Core.Cells.{Cell, Empty}


object GameCore {

  //T1
  def randomMove (board: Board, rand: MyRandom):((Int, Int),MyRandom) = {
    val validCells = filterCells(board,Empty) //lista celulas vazias validas para jogar
    val (n, nextRnd) = rand.nextInt(validCells.length) // escolher um número entre 0 e o máx da lista (será o indice)
    (validCells(n),nextRnd.asInstanceOf[MyRandom]) //posição aleatória da lista de celulas vazias, nextRandom
  }

  //T2
  def play(board: Board, player: Cells.Cell, row: Int, col: Int): Board = {
    board.zipWithIndex.map{ //obter uma lista de tuplos (lista(cell),index da linha)
      case (lstCell,rowIndex) => if (rowIndex == row) { // se o index da linha igual a row
        lstCell.zipWithIndex.map{ //obter uma lista de tuplos (cell, index da coluna)
          case (cell,index) => if (index == col) player else cell //se index da coluna igual a col, criar novo board com
                                                                  // a cell na posição (row,col) igual a player
        }
      } else lstCell
    }
  }


  def filterCells (board: Board, cell: Cell): List[(Int,Int)] = {
    board.zipWithIndex.flatMap{
      case (subList, row) => subList.zipWithIndex.collect {
        case (cell1, col) if cell1 equals cell => (row,col)
      }
    }
  }

}
