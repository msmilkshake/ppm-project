package tui

import core.Cells.Empty
import core.Difficulty._
import core.GameLogic.setBoardCell
import core.ProgramState._
import core.{GameLogic, GameState, MyRandom, Settings}
import io.{BoardPrinter, IOUtils, Serializer}

import scala.annotation.tailrec

object HexTUI extends App {

  val mainMenuTitle = "Main Menu"
  val settingsMenuTitle = "Settings Menu"

  val random = MyRandom(IOUtils.loadSeed())
  val boardLen = 5
  val difficulty = Easy
  val initialState = GameState(None, difficulty, random, None)
  val defaultSettings = Settings(boardLen, difficulty)
  val defaultContainer = Container(initialState, Nil, InMainMenu, defaultSettings)

  val container = IOUtils.checkHasContinue() match {
    case true => Serializer.getLastSavedGame(defaultContainer)
    case _ => defaultContainer
  }


  // -- Program Entry Point --
  mainLoop(container)

  @tailrec
  def mainLoop(c: Container): Unit = {
    c.programState match {
      case InMainMenu =>
        val mainMenu = c.gameState.board match {
          case Some(_) => Menu.mainWithContinue
          case None => Menu.mainWithoutContinue
        }

        IOUtils.optionPrompt(mainMenuTitle, mainMenu) match {
          case None =>
            IOUtils.warningInvalidOption()
            mainLoop(c)
          case Some(option) =>
            mainLoop(option.exec(c))
        }

      case InSettings =>
        IOUtils.optionPrompt(settingsMenuTitle, Menu.settingsMenu) match {
          case None =>
            IOUtils.warningInvalidOption()
            mainLoop(c)
          case Some(option) =>
            mainLoop(option.exec(c))
        }

      case GameRunning =>
        mainLoop(GameLogic.playTurn(c))

      case UndoMove =>
        c.playHistory match {
          case Nil =>
            IOUtils.displayNoUndoMoves()
            mainLoop(Container(
              c.gameState,
              c.playHistory,
              GameRunning,
              c.newGameSettings))

          case (cRow, cCol) :: (pRow, pCol) :: tail =>
            IOUtils.displayUndoSuccess()
            val b1 = setBoardCell(c.gameState.board.get, Empty, cRow, cCol)
            val b2 = setBoardCell(b1, Empty, pRow, pCol)
            mainLoop(Container(GameState(
              Some(b2),
              c.gameState.difficulty,
              c.gameState.random,
              c.gameState.winner),
              tail,
              GameRunning,
              c.newGameSettings))
        }

      case SaveGame =>
        Serializer.saveGame(c)
        mainLoop(Container(c.gameState,
          c.playHistory,
          GameRunning,
          c.newGameSettings))

      case GameWon =>
        BoardPrinter.printBoard(c.gameState.board.get)
        IOUtils.displayWinner(c.gameState.winner.get)
        val nextState = IOUtils.promptEndGameOption()
        IOUtils.deleteFileQuiet()

        mainLoop(Container(
          GameState(None,
            c.gameState.difficulty,
            c.gameState.random,
            None),
          Nil,
          nextState,
          c.newGameSettings))

      case Exit =>
        c.gameState.board match {
          case Some(_) => Serializer.saveGameAuto(c)
          case None =>
        }
        IOUtils.saveSeed(c.gameState.random.getSeed())
        IOUtils.displayGoodbyeMessage()
    }
  }

}
