package logic

object ProgramState extends Enumeration {
  
  type ProgramState = Value
  val MainMenu, Settings, GameRunning, Undo, GameWon, Exit = Value
  
}
