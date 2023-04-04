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

  def drawBoardBody(len: Int, cellLst: List[Cell], lineString: String): Unit = {
    val iniLine = redColor("* ")
    val endLine = redColor(" *")



    def drawCell(cellLst: List[Cell], cellsString: String):String = cellLst match {
        case Nil => cellsString
        case c::cs => {
          val cell = c match {
            case Red => redColor("X")
            case Blue => blueColor("O")
            case _ => "."
          }
          drawCell(cs,f"${cellsString} - ${cell}")
        }
    }

    def drawPatternBody(col: Int, lineString: String): String = {
      val iniOffset = " "
      val offset = "  "
      val bar = "\\"
      len match {
        case 0 => f"${iniOffset}${offset}${bar}${lineString}"
        case _ => drawPatternBody(col - 1, f"${lineString} / \\")
      }

    }

    val first: String = cellLst.head match {
      case Red => redColor("X")
      case Blue => blueColor("O")
      case _ => "."
    }

    println(f"${iniLine}${first}${drawCell(cellLst.tail,lineString)}${endLine}")
    println(drawPatternBody(len,lineString))
  }

}
