package tui

import io.{BoardPrinter, IOUtils}
import logic.Cells.{Empty, initBoard}
import logic.Difficulty._
import logic.ProgramState._
import logic.{GameLogic, GameState, MyRandom}

import scala.annotation.tailrec

object Hex extends App {

  val mainMenuTitle = "Main Menu"
  val settingsMenuTitle = "Settings Menu"

  val random = MyRandom(0x54321)
  val boardLen = 7
  val board = initBoard(boardLen)
  val difficulty = Easy
  val winner = Empty

  val saveExists = IOUtils.checkSaveExists()

  val s = GameState(boardLen, board, difficulty, random, winner)
  val startingContainer = Container(s, Nil, MainMenu, saveExists)


  // -- Program Entry Point --
  mainLoop(startingContainer)

  @tailrec
  def mainLoop(cont: Container): Unit = {
    cont.programState match {
      case MainMenu =>
        val mainMenu =
          if (cont.saveExists) Menu.mainWithSavedGame
          else Menu.mainWithoutSavedGame

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

      case Undo =>
        ???

      case GameWon =>
        BoardPrinter.printBoard(cont.gameState.board)
        val nextState = IOUtils.displayWinner(cont.gameState.winner)
        IOUtils.deleteFileQuiet()

        mainLoop(
          Container(
            GameState(cont.gameState.boardLen,
              initBoard(cont.gameState.boardLen),
              cont.gameState.computerDifficulty,
              cont.gameState.random,
              Empty),
            Nil, nextState, IOUtils.checkSaveExists())
        )

      case Exit => IOUtils.displayGoodbyeMessage()
    }
  }

}
