package IO

import Core.Cells.{Blue, Cell, Empty, Red}
import IO.BoardDrawer.{drawBoard, drawBoardBody, drawBoardFooter, drawBoardHeader}

object Test {

  def main(args: Array[String]): Unit = {
    def b1 = List(List(Red,Empty,Blue),List(Empty,Empty,Empty),List(Empty,Blue,Empty))
    def b2 = List(List(Red,Empty,Blue,Empty),List(Empty,Empty,Empty,Blue),List(Empty,Blue,Empty,Red),List(Empty,Empty,Empty,Empty))

    drawBoard(b2)
  }

}
