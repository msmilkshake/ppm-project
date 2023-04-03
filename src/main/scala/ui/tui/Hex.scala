package ui.tui

import io.BoardPrinter
import io.IOUtils._
import logic.Cells.initBoard
import logic.PlayerType._
import logic.PlayerType.Human
import logic.ProgramState._
import logic.{GameState, MyRandom, ProgramState}

import scala.annotation.tailrec

object Hex extends App {
  
  val random = MyRandom(0x54321)
  val boardLen = 7
  val board = initBoard(boardLen)
  val players = (Human, Easy)
  val firstPlayer = 1
  
  val s = GameState(boardLen, board, players, random, firstPlayer, true)
  val cont = GameContainer(s, MainMenu)
  
  // -- Program Entry Point --
  mainLoop(cont)
  
  
  def mainLoop(cont: GameContainer): Unit = {
    cont.ps match {
      case MainMenu =>
        optionPrompt(Menu.mainWithoutSavedGame) match {
          case None =>
            warningInvalidOption()
            mainLoop(cont)
          case Some(option) =>
            mainLoop(option.exec(cont))
        }
      case Settings =>
        optionPrompt(Menu.settingsMenu) match {
          case None =>
            warningInvalidOption()
            mainLoop(cont)
          case Some(option) =>
            mainLoop(option.exec(cont))
        }
      case GameRunning => ???
      case Exit => 
    }
    
  }
  
  def gameLoop(state: GameState): Unit = {
    BoardPrinter.printBoard(state.board)
  }
  
}
