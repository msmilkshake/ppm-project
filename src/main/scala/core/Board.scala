package core

import core.Cells.{Cell, Empty}

object Board {
  type Board = List[List[Cell]]

  def firstBoard (len: Int): Board = {

    def buildRow(row: List[Cell], i: Int): List[Cell] = i match {
      case 0 => row
      case _ => buildRow(Empty::row,i - 1)
    }


    def buildBoard(board:Board, i: Int): Board = i match {
      case 0 => board
      case _ => buildBoard(buildRow(Nil,len)::board, i - 1 )
    }
    buildBoard(Nil, len)
  }

}
