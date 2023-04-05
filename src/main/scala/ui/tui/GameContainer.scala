package ui.tui

import io.IOUtils
import logic.Difficulty.Difficulty
import logic.ProgramState.ProgramState
import logic.{Cells, GameState, ProgramState}

import scala.annotation.tailrec

case class GameContainer(gameState: GameState,
                         stateHistory: List[GameState],
                         programState: ProgramState,
                         saveExists: Boolean) {
}

object GameContainer {

  val labels = (
    "Choose Computer Difficulty", //  1
    ""
  )

  @tailrec
  def navToSetPlayer2Type()(c: GameContainer): GameContainer = {
    IOUtils.optionPrompt(labels._1, Menu.setPlayer2Type) match {
      case None =>
        IOUtils.warningInvalidOption()
        navToSetPlayer2Type()(c)
      case Some(option) => option.exec(c)
    }
  }

  def startNewGame()(c: GameContainer): GameContainer = {
    GameContainer(c.gameState, c.stateHistory, ProgramState.GameRunning, c.saveExists)
  }

  def navToSettings()(c: GameContainer): GameContainer = {
    GameContainer(c.gameState, c.stateHistory, ProgramState.Settings, c.saveExists)
  }

  def navToMainMenu()(c: GameContainer): GameContainer = {
    GameContainer(c.gameState, c.stateHistory, ProgramState.MainMenu, c.saveExists)
  }

  def setGameBoardLength()(c: GameContainer): GameContainer = {
    val length = IOUtils.promptBoardLen()
    GameContainer(
      GameState(length,
        Cells.initBoard(length),
        c.gameState.computerDifficulty,
        c.gameState.random),
      c.stateHistory,
      ProgramState.Settings,
      c.saveExists)
  }

  def deleteSavedGame()(c: GameContainer): GameContainer = {
    IOUtils.deleteSaveFile()
    GameContainer(
      GameState(c.gameState.boardLen,
        c.gameState.board,
        c.gameState.computerDifficulty,
        c.gameState.random),
      c.stateHistory,
      ProgramState.Settings,
      IOUtils.checkSaveExists())
  }

  def setDifficulty(d: Difficulty)(c: GameContainer): GameContainer = {
    GameContainer(
      GameState(c.gameState.boardLen,
        c.gameState.board,
        d,
        c.gameState.random),
      c.stateHistory,
      ProgramState.Settings,
      c.saveExists)
  }

  def showCurrSettings()(c: GameContainer): GameContainer = {
    IOUtils.displayCurrentSettings(c.gameState);
    c
  }

  def exitProgram()(c: GameContainer): GameContainer = {
    GameContainer(c.gameState, c.stateHistory, ProgramState.Exit, c.saveExists)
  }

}
