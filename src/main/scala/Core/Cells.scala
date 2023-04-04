package Core

object Cells extends Enumeration {

  type Board = List[List[Cells.Cell]]
  type Cell = Value
  val Red, Blue, Empty = Value
}
