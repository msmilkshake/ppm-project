package ui.tui

import io.{BoardPrinter, IOUtils}
import logic.Cells.initBoard
import logic.PlayerType.{Human, _}
import logic.ProgramState._
import logic.{GameLogic, GameState, MyRandom}

object Hex extends App {
  
  val mainMenuTitle = "Main Menu"
  val settingsMenuTitle = "Settings Menu"
  
  val random = MyRandom(0x54321)
  val boardLen = 7
  val board = initBoard(boardLen)
  val players = (Human, Easy)
  val firstPlayer = 1
  val isRandom = false
  
  val saveExists = IOUtils.checkSaveExists()
  
  val s = GameState(boardLen, board, players, random, firstPlayer, firstPlayer, isRandom, saveExists)
  val cont = GameContainer(s, Nil, MainMenu)
  
  
  // -- Program Entry Point --
  mainLoop(cont)
  
  
  def mainLoop(cont: GameContainer): Unit = {
    cont.ps match {
      case MainMenu =>
        val mainMenu = cont.gs.saveExists match {
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
  
  def gameLoop(state: GameState): Unit = {
    BoardPrinter.printBoard(state.board)
  }
  
}
