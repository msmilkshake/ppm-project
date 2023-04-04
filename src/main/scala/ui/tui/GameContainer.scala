package ui.tui

import io.IOUtils
import logic.PlayerType.PlayerType
import logic.ProgramState.ProgramState
import logic.{Cells, GameState, MyRandom, ProgramState}

import scala.annotation.tailrec

case class GameContainer(gs: GameState, h: List[GameState], ps: ProgramState) {
  def randomizeStartingPlayer(): GameContainer =
    GameContainer.randomizeStartingPlayer(this)
}

object GameContainer {

  val labels = (
    "Choose Player Type for Player 2",    //  1
    "Choose Which Player Plays First",    //  2
  )
  
  @tailrec
  def navToSetPlayer2Type()(c: GameContainer):GameContainer = {
    IOUtils.optionPrompt(labels._1, Menu.setPlayer2Type) match {
      case None =>
        IOUtils.warningInvalidOption()
        navToSetPlayer2Type()(c)
      case Some(option) => option.exec(c)
    }
  }

  @tailrec
  def navToSetWhoPlaysFirst()(c: GameContainer): GameContainer = {
    IOUtils.optionPrompt(labels._2, Menu.setWhoPlaysFirst) match {
      case None =>
        IOUtils.warningInvalidOption()
        navToSetWhoPlaysFirst()(c)
      case Some(option) => option.exec(c)
    }
  }

  def setStartingPlayer(n: Int)(c: GameContainer): GameContainer = {
    val isRandom = n match {
      case 0 => true
      case _ => false
    }
    GameContainer(
      GameState(c.gs.boardLen,
        c.gs.board,
        c.gs.players,
        c.gs.random,
        n,
        n,
        isRandom,
        c.gs.saveExists),
      c.h,
      ProgramState.Settings)
  }

  def startNewGame()(c: GameContainer): GameContainer = {
    GameContainer(c.gs, c.h, ProgramState.StartGame)
  }

  def navToSettings()(c: GameContainer): GameContainer = {
    GameContainer(c.gs, c.h, ProgramState.Settings)
  }

  def navToMainMenu()(c: GameContainer): GameContainer = {
    GameContainer(c.gs, c.h, ProgramState.MainMenu)
  }

  def setGameBoardLength()(c: GameContainer): GameContainer = {
    val length = IOUtils.promptBoardLen()
    GameContainer(
      GameState(length,
        Cells.initBoard(length),
        c.gs.players,
        c.gs.random,
        c.gs.firstPlayer,
        c.gs.turn,
        c.gs.isRandom,
        c.gs.saveExists),
      c.h,
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
        c.gs.turn,
        c.gs.isRandom,
        IOUtils.checkSaveExists()),
      c.h,
      ProgramState.Settings)
  }

  def setPlayer2(t: PlayerType)(c: GameContainer): GameContainer = {
    GameContainer(
      GameState(c.gs.boardLen,
        c.gs.board,
        (c.gs.players._1, t),
        c.gs.random,
        c.gs.firstPlayer,
        c.gs.turn,
        c.gs.isRandom,
        c.gs.saveExists),
      c.h,
      ProgramState.Settings)
  }

  def showCurrSettings()(c: GameContainer): GameContainer = {
    IOUtils.displayCurrentSettings(c.gs); c
  }

  def exitProgram()(c: GameContainer): GameContainer = {
    GameContainer(c.gs, c.h, ProgramState.Exit)
  }

  def randomizeStartingPlayer(c: GameContainer): GameContainer = {
    val (player, newRand) = c.gs.random.nextInt(2)
    GameContainer(
      GameState(c.gs.boardLen,
        c.gs.board,
        c.gs.players,
        newRand.asInstanceOf[MyRandom],
        player + 1,
        player + 1,
        c.gs.isRandom,
        c.gs.saveExists),
      c.h,
      ProgramState.GameRunning)
  }

}
