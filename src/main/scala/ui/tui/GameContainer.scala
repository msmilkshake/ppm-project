package ui.tui

import io.IOUtils.{optionPrompt, warningInvalidOption}
import io.{BoardPrinter, IOUtils}
import logic.PlayerType.PlayerType
import logic.{Cells, GameState, ProgramState}
import logic.ProgramState.ProgramState
import ui.tui.Menu.setPlayer2Type

import scala.annotation.tailrec

case class GameContainer(gs: GameState, ps: ProgramState) {
}

object GameContainer {

  val labels = (
    "Choose Player Type for Player 2",
    "Choose Which Player Plays First",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
    "",
  )
  
  @tailrec
  def navToSetPlayer2Type()(c: GameContainer):GameContainer = {
    optionPrompt(labels._1, Menu.setPlayer2Type) match {
      case None =>
        warningInvalidOption()
        navToSetPlayer2Type()(c)
      case Some(option) => option.exec(c)
    }
  }

  @tailrec
  def navToSetWhoPlaysFirst()(c: GameContainer): GameContainer = {
    optionPrompt(labels._1, Menu.setWhoPlaysFirst) match {
      case None =>
        warningInvalidOption()
        navToSetWhoPlaysFirst()(c)
      case Some(option) => option.exec(c)
    }
  }

  def setStartingPlayer(n: Int)(c: GameContainer): GameContainer = {
    GameContainer(
      GameState(c.gs.boardLen,
        c.gs.board,
        c.gs.players,
        c.gs.random,
        n,
        c.gs.saveExists,
        c.gs.movesHistory),
      ProgramState.Settings)
  }

  def startNewGame()(c: GameContainer): GameContainer = {
    GameContainer(c.gs, ProgramState.GameRunning)
  }

  def navToSettings()(c: GameContainer): GameContainer = {
    GameContainer(c.gs, ProgramState.Settings)
  }

  def navToMainMenu()(c: GameContainer): GameContainer = {
    GameContainer(c.gs, ProgramState.MainMenu)
  }

  def setGameBoardLength()(c: GameContainer): GameContainer = {
    val length = IOUtils.promptBoardLen()
    GameContainer(
      GameState(length,
        Cells.initBoard(length),
        c.gs.players,
        c.gs.random,
        c.gs.firstPlayer,
        c.gs.saveExists,
        c.gs.movesHistory),
      ProgramState.Settings)
  }
  
  def deleteSavedGame()(c: GameContainer): GameContainer = {
    IOUtils.deleteSaveFile()
    GameContainer(
      GameState(c.gs.boardLen,
        c.gs.board,
        c.gs.players,
        c.gs.random,
        c.gs.firstPlayer,
        IOUtils.checkSaveExists(),
        c.gs.movesHistory),
      ProgramState.Settings)
  }

  def setPlayer2(t: PlayerType)(c: GameContainer): GameContainer = {
    GameContainer(
      GameState(c.gs.boardLen,
        c.gs.board,
        (c.gs.players._1, t),
        c.gs.random,
        c.gs.firstPlayer,
        c.gs.saveExists,
        c.gs.movesHistory),
      ProgramState.Settings)
  }

  def showCurrSettings()(c: GameContainer): GameContainer = {
    IOUtils.displayCurrentSettings(c.gs); c
  }

  def exitProgram()(c: GameContainer): GameContainer = {
    GameContainer(c.gs, ProgramState.Exit)
  }

}
