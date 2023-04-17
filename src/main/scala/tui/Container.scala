package tui

import core.Difficulty.Difficulty
import core.ProgramState.{GameRunning, ProgramState}
import core.{Board, GameState, ProgramState, Settings}
import io.{IOUtils, Serializer}

import scala.annotation.tailrec

case class Container(gameState: GameState,
                     stateHistory: List[GameState],
                     programState: ProgramState,
                     newGameSettings: Settings)

object Container {

  val labels = (
    "Choose Computer Difficulty",    //  1
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
    Container(GameState(
      Some(Board.initBoard(c.newGameSettings.boardLength)),
      c.newGameSettings.difficulty,
      c.gameState.random,
      c.gameState.winner),
      c.stateHistory,
      ProgramState.GameRunning,
      c.newGameSettings)
  }

  def resumeGame()(c: Container): Container = {
    Container(c.gameState,
      c.stateHistory,
      GameRunning,
      c.newGameSettings)
  }

  def navToSettings()(c: Container): Container = {
    Container(c.gameState,
      c.stateHistory,
      ProgramState.InSettings,
      c.newGameSettings)
  }

  def navToMainMenu()(c: Container): Container = {
    Container(c.gameState,
      c.stateHistory,
      ProgramState.InMainMenu,
      c.newGameSettings)
  }

  def setGameBoardLength()(c: Container): Container = {
    Container(c.gameState,
      c.stateHistory,
      ProgramState.InSettings,
      Settings(IOUtils.promptBoardLen(),
        c.newGameSettings.difficulty))
  }

  def deleteSavedGame()(c: Container): Container = {
    IOUtils.deleteContinueFile()
    Container(
      GameState(
        c.gameState.board,
        c.gameState.difficulty,
        c.gameState.random,
        c.gameState.winner),
      c.stateHistory,
      ProgramState.InSettings,
      c.newGameSettings)
  }

  def setDifficulty(d: Difficulty)(c: Container): Container = {
    Container(c.gameState,
      c.stateHistory,
      ProgramState.InSettings,
      Settings(c.newGameSettings.boardLength, d))
  }

  def loadGame()(c: Container): Container = {
    IOUtils.promptLoadGame() match {
      case input if input.isBlank => c
      case filename =>
        Serializer.getSavedGame(f"${IOUtils.saveFolderPath}$filename", c, GameRunning)
    }
  }

  def showCurrSettings()(c: Container): Container = {
    IOUtils.displayNewGameSettings(c.newGameSettings)
    c
  }

  def exitProgram()(c: Container): Container = {
    Container(c.gameState,
      c.stateHistory,
      ProgramState.Exit,
      c.newGameSettings)
  }

}
