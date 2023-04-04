package IO

import Core.Cells.{Blue, Board, Cell, Red}
import IO.ColorUtils._

object BoardDrawer {

  def drawBoard(board: Board): Unit = {

  }

  def drawBoardHeader(len: Int): Unit = {
    val IniOffSet = blueColor(" *")
    val HeadPattern = blueColor("   *")

    println(f"${IniOffSet}${HeadPattern * (len - 1)}")
  }

  def drawBoardBody(cellLst: List[Cell], lineString: String): Unit = {

    def drawCell(cellLst: List[Cell], cellsString: String):String = cellLst match {
        case Nil => cellsString
        case c::cs => {
          val cell = c match {
            case Red => redColor("X")
            case Blue => blueColor("O")
            case _ => "."
          }
          drawCell(cs,f"${cellsString}${cell}")
        }
    }

    def drawPatternBody(len: Int, lineString: String): String = {

    }
    println(drawCell(cellLst,lineString))

  }

}
