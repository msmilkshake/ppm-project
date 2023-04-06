package Core

import Core.Board.Board
import Core.Cells.Cell


object GCore {


  def filterCells (board: Board, cell: Cell): List[(Int,Int)] = {
    board.zipWithIndex.flatMap{
      case (subList, row) => subList.zipWithIndex.collect {
        case (cell1, col) if cell1 equals cell => (row,col)
      }
    }
  }

}
