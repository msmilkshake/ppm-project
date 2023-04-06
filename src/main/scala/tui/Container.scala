package tui

import io.{SaveState, IOUtils}
import logic.Difficulty.Difficulty
import logic.ProgramState.ProgramState
import logic.{Cells, GameState, ProgramState}

import scala.annotation.tailrec

case class Container(gameState: GameState,
                     stateHistory: List[GameState],
                     programState: ProgramState,
                     saveExists: Boolean)

object Container {

  val labels = (
    "Choose Computer Difficulty", //  1
    ""
  )

  @tailrec
  def navToSetPlayer2Type()(c: Container): Container = {
    IOUtils.optionPrompt(labels._1, Menu.setPlayer2Type) match {
      case None =>
        IOUtils.warningInvalidOption()
        navToSetPlayer2Type()(c)
      case Some(option) => option.exec(c)
    }
  }

  def startNewGame()(c: Container): Container = {
    Container(c.gameState, c.stateHistory, ProgramState.GameRunning, c.saveExists)
  }

  def resumeGame()(c: Container): Container = {
    SaveState.getSavedGame()
  }

  def navToSettings()(c: Container): Container = {
    Container(c.gameState, c.stateHistory, ProgramState.Settings, c.saveExists)
  }

  def navToMainMenu()(c: Container): Container = {
    Container(c.gameState, c.stateHistory, ProgramState.MainMenu, c.saveExists)
  }

  def setGameBoardLength()(c: Container): Container = {
    val length = IOUtils.promptBoardLen()
    Container(
      GameState(length,
        Cells.initBoard(length),
        c.gameState.computerDifficulty,
        c.gameState.random,
        c.gameState.winner),
      c.stateHistory,
      ProgramState.Settings,
      c.saveExists)
  }

  def deleteSavedGame()(c: Container): Container = {
    IOUtils.deleteSaveFile()
    Container(
      GameState(c.gameState.boardLen,
        c.gameState.board,
        c.gameState.computerDifficulty,
        c.gameState.random,
        c.gameState.winner),
      c.stateHistory,
      ProgramState.Settings,
      IOUtils.checkSaveExists())
  }

  def setDifficulty(d: Difficulty)(c: Container): Container = {
    Container(
      GameState(c.gameState.boardLen,
        c.gameState.board,
        d,
        c.gameState.random,
        c.gameState.winner),
      c.stateHistory,
      ProgramState.Settings,
      c.saveExists)
  }

  def showCurrSettings()(c: Container): Container = {
    IOUtils.displayCurrentSettings(c.gameState)
    c
  }

  def exitProgram()(c: Container): Container = {
    Container(c.gameState, c.stateHistory, ProgramState.Exit, c.saveExists)
  }

}
