package IO

import Core.Cells.{Blue, Cell, Empty, Red}
import IO.BoardDrawer.{drawBoard, drawBoardBody, drawBoardFooter, drawBoardHeader}

object Test {

  def main(args: Array[String]): Unit = {
    def b1 = List(List(Red,Empty,Blue),List(Empty,Empty,Empty),List(Empty,Blue,Empty))

    drawBoard(b1)
  }

}
