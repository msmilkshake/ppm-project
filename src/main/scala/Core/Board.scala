package Core

import Core.Cells.{Cell, Empty}

object Board {
  type Board = List[List[Cell]]

  def firstBoard (len: Int): Board = {

    def buildRow(row: List[Cell], len: Int): List[Cell] = len match {
      case 0 => row
      case _ => buildRow(Empty::row,len - 1)
    }


    def buildBoard(board:Board, len: Int): Board = len match {
      case 0 => board
      case _ => buildBoard(buildRow(Nil,len)::board, len - 1 )
    }
    buildBoard(Nil, len)
  }

}
