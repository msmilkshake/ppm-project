package IO

import Core.Cells.{Blue, Cell, Empty, Red}
import IO.BoardDrawer.{drawBoardBody, drawBoardFooter, drawBoardHeader}

object Test {

  def main(args: Array[String]): Unit = {
    drawBoardHeader(3)
    drawBoardBody(List(List(Red,Empty,Blue),List(Empty,Empty,Empty),List(Empty,Blue,Empty)),2,List.range(0, 3))
    drawBoardFooter(3)
  }

}
