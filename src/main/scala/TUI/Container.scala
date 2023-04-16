package TUI

import Core.Board.firstBoard
import Core.Cells.{Blue, Empty, Red}
import Core.{GameState, ProgramState}
import Core.ProgramState.{Exit, Menu, ProgramState, Running, Settings}
import IO.IOUtils.{getBoardLen, getUserInputInt}

//código da ficha 6
case class Container(gs: GameState, history: List[GameState], ps: ProgramState, len: Int)

object Container {
  def setBoardLen()(c: Container): Container = {
    val n = getBoardLen("Enter lenght")
    Container(c.gs, c.history, Menu, n)
  }

  def exit()(c: Container): Container = {
    Container(c.gs, c.history, Exit, c.len)
  }
  def backToMain()(c: Container): Container= {
    Container(c.gs, c.history,Menu, c.len)
  }

  def settings()(c: Container): Container= {
    Container(c.gs,c.history,Settings, c.len)
  }

  def startNewGame()(c: Container): Container= {
    val gs = GameState (firstBoard(c.len),c.gs.rnd,Empty)
    Container(gs,Nil,Running, c.len)
  }
}
