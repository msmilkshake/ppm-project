package io

import logic.GameState
import tui.Container

object StateSaver {

  def serializeContainer(c: Container): Unit = {
    val header = f"${c.programState match {
      case logic.ProgramState.MainMenu => 1
      case logic.ProgramState.Settings => 2
      case logic.ProgramState.GameRunning => 3
      case logic.ProgramState.GameWon => 4
      case logic.ProgramState.Exit => 5
    }}\n"
    val curState = serializeState(c.gameState)
  }
  def serializeState(gs: GameState): String = {
    ???
  }

}
