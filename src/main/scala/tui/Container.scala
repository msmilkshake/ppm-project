package tui

import core.Coord.Coord
import core.Difficulty.Difficulty
import core.ProgramState.{GameRunning, ProgramState}
import core.{Board, GameState, ProgramState, Settings}
import io.{IOUtils, Serializer}

import scala.annotation.tailrec

case class Container(gameState: GameState,
                     playHistory: List[Coord],
                     programState: ProgramState,
                     newGameSettings: Settings)

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
    Container(GameState(
      Some(Board.initBoard(c.newGameSettings.boardLength)),
      c.newGameSettings.difficulty,
      c.gameState.random,
      c.gameState.winner),
      c.playHistory,
      ProgramState.GameRunning,
      c.newGameSettings)
  }

  def resumeGame()(c: Container): Container = {
    Container(c.gameState,
      c.playHistory,
      GameRunning,
      c.newGameSettings)
  }

  def navToSettings()(c: Container): Container = {
    Container(c.gameState,
      c.playHistory,
      ProgramState.InSettings,
      c.newGameSettings)
  }

  def navToMainMenu()(c: Container): Container = {
    Container(c.gameState,
      c.playHistory,
      ProgramState.InMainMenu,
      c.newGameSettings)
  }

  def setGameBoardLength()(c: Container): Container = {
    Container(c.gameState,
      c.playHistory,
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
      c.playHistory,
      ProgramState.InSettings,
      c.newGameSettings)
  }

  def setDifficulty(d: Difficulty)(c: Container): Container = {
    Container(c.gameState,
      c.playHistory,
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
      c.playHistory,
      ProgramState.Exit,
      c.newGameSettings)
  }

}
