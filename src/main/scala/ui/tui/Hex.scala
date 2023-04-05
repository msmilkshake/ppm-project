package ui.tui

import io.{BoardPrinter, IOUtils}
import logic.Cells.initBoard
import logic.Difficulty._
import logic.ProgramState._
import logic.{GameLogic, GameState, MyRandom}

object Hex extends App {
  
  val mainMenuTitle = "Main Menu"
  val settingsMenuTitle = "Settings Menu"
  
  val random = MyRandom(0x54321)
  val boardLen = 7
  val board = initBoard(boardLen)
  val difficulty = Easy
  
  val saveExists = IOUtils.checkSaveExists()
  
  val s = GameState(boardLen, board, difficulty, random)
  val startingContainer = GameContainer(s, Nil, MainMenu, saveExists)
  
  
  // -- Program Entry Point --
  mainLoop(startingContainer)
  
  
  def mainLoop(cont: GameContainer): Unit = {
    cont.programState match {
      case MainMenu =>
        val mainMenu = cont.saveExists match {
          case true => Menu.mainWithSavedGame
          case false => Menu.mainWithoutSavedGame
        }

        IOUtils.optionPrompt(mainMenuTitle, mainMenu) match {
          case None =>
            IOUtils.warningInvalidOption()
            mainLoop(cont)
          case Some(option) =>
            mainLoop(option.exec(cont))
        }
        
      case Settings =>
        IOUtils.optionPrompt(settingsMenuTitle, Menu.settingsMenu) match {
          case None =>
            IOUtils.warningInvalidOption()
            mainLoop(cont)
          case Some(option) =>
            mainLoop(option.exec(cont))
        }
      
      case GameRunning =>
        mainLoop(GameLogic.playTurn(cont))

      case GameWon =>
      case Exit => 
    }
    
  }
  
}
