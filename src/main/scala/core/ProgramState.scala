package core

object ProgramState extends Enumeration {
  
  type ProgramState = Value
  val InMainMenu, InSettings, GameRunning, UndoMove, SaveGame, GameWon, Exit = Value
  
}
