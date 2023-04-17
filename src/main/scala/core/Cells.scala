package core

import core.Board.Board

import scala.annotation.tailrec

object Cells extends Enumeration {
  
  type Cell = Value

  val Red, Blue, Empty = Value
  
}
