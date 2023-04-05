package IO

import Core.Cells.{Blue, Cell, Empty, Red}
import IO.BoardDrawer.{drawBoardBody, drawBoardFooter, drawBoardHeader}

object Test {

  def main(args: Array[String]): Unit = {
    drawBoardHeader(5)
    drawBoardBody(4,List(Red,Empty,Blue,Empty,Empty),"")
    drawBoardFooter(5)
  }

}
