package IO

import Core.Cells.{Blue, Board, Cell, Empty, Red}
import IO.ColorUtils._

import scala.annotation.tailrec

object BoardDrawer {

  def drawBoard(board: Board): Unit = {
    drawBoardHeader(board.length)
    drawBoardBody(board,board.length-1, List.range(0, board.length))
    drawBoardFooter(board.length)
  }

  def drawBoardHeader(len: Int): Unit = {
    val IniOffSet = blueColor(" *")
    val HeadPattern = blueColor("   *")

    println(f"${IniOffSet}${HeadPattern * (len - 1)}")
  }

  def drawBoardBody(board: Board, len: Int, index: List[Int]): Unit = {
    val iniLine = redColor("* ")
    val endLine = redColor(" *")
    val iniOffset = " "
    val offset = "  "


    @tailrec
    def drawCell(cellLst: List[Cell], cellsString: String): String = cellLst match {
      case Nil => cellsString
      case c :: cs => {
        val cell = c match {
          case Red => redColor("X")
          case Blue => blueColor("O")
          case _ => "."
        }
        drawCell(cs, f"${cellsString} - ${cell}")
      }
    }

    @tailrec
    def drawPatternBody(col: Int, lineString: String): String = {
      val bar = "\\"
      col match {
        case 0 => f"${bar}${lineString}"
        case _ => drawPatternBody(col - 1, f"${lineString} / \\")
      }

    }

    (board,index) match {
      case (x :: Nil,i::Nil) => {
        val first1: String = x.head match {
          case Red => redColor("X")
          case Blue => blueColor("O")
          case _ => "."
        }
        println(f"${"  " * i}${iniLine}${first1}${drawCell(x.tail, "")}${endLine}")
      }
      case (x :: xs, i::is) => {
        val first: String = x.head match {
          case Red => redColor("X")
          case Blue => blueColor("O")
          case _ => "."
        }
        println(f"${"  " * i}${iniLine}${first}${drawCell(x.tail, "")}${endLine}")
        println(f"${iniOffset}${"  " * i}${offset}${drawPatternBody(len,"")}")
        drawBoardBody(xs,len,is)
      }
    }
  }

  def drawBoardFooter(len: Int): Unit ={
    val IniFootOffSet = blueColor(" *")
    val FootPattern = blueColor("   *")

    println(f"${"  " * len}${IniFootOffSet}${FootPattern * (len - 1)}")

  }

}
