package io

import logic.GameState
import ui.tui.GameContainer

object StateSaver {

  def serializeContainer(c: GameContainer): Unit = {
    val header = f"${c.programState match {
      case logic.ProgramState.MainMenu => 1
      case logic.ProgramState.Settings => 2
      case logic.ProgramState.StartGame => 3
      case logic.ProgramState.GameRunning => 4
      case logic.ProgramState.GameWon => 5
      case logic.ProgramState.Exit => 6
    }}\n"
    val curState = serializeState(c.gameState)
  }
  def serializeState(gs: GameState): String = {
    ???
  }

}
