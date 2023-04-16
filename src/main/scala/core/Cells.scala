package core

import core.Board.Board

import scala.annotation.tailrec

object Cells extends Enumeration {
  
  type Cell = Value

  val Red, Blue, Empty = Value

  def initBoard(len: Int): Board = {

    @tailrec
    def initRow(row: List[Cell], i: Int): List[Cell] = {
      i match {
        case 0 => row
        case _ => initRow(Empty :: row, i - 1)
      }
    }

    @tailrec
    def initRows(board: Board, i: Int): Board = {
      i match {
        case 0 => board
        case _ => initRows(initRow(Nil, len) :: board, i - 1)
      }
    }

    initRows(Nil, len)
  }
}
