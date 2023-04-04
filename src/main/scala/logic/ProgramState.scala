package logic

object ProgramState extends Enumeration {
  
  type ProgramState = Value
  val MainMenu, Settings, StartGame, GameRunning, GameWon, Exit = Value
  
}
