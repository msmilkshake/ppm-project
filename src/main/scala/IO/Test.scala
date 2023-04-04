package IO

import Core.Cells.{Blue, Cell, Empty, Red}
import IO.BoardDrawer.{drawBoardBody, drawBoardHeader}

object Test {

  def main(args: Array[String]): Unit = {
    drawBoardHeader(5)
    drawBoardBody(List(Red,Empty,Blue,Empty,Empty),"")
  }

}
