package ui.tui

import io.{BoardPrinter, IOUtils}
import logic.{Cells, GameState, ProgramState}
import logic.ProgramState.ProgramState

case class GameContainer(gs: GameState, ps: ProgramState) {

}

object GameContainer {
  def startNewGame()(container: GameContainer): GameContainer = {
    BoardPrinter.printBoard(container.gs.board)
    container
  }


  def navToSettings()(container: GameContainer): GameContainer = {
    GameContainer(container.gs, ProgramState.Settings)
  }

  def navToMainMenu()(container: GameContainer): GameContainer = {
    GameContainer(container.gs, ProgramState.MainMenu)
  }

  def setGameBoardLength()(c: GameContainer): GameContainer = {
    val length = IOUtils.promptBoardLen()
    GameContainer(
      GameState(length,
        Cells.initBoard(length),
        c.gs.players, c.gs.random,
        c.gs.firstPlayer, c.gs.running),
      ProgramState.Settings)
  }

}
