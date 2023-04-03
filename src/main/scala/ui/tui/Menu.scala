package ui.tui

import logic.GameState

import scala.collection.immutable.ListMap

object Menu {
  val s1 = "Start New Game"
  val s2 = "Continue Last Game"
  val s3 = "Game Settings"
  val s4 = "Quit"

  val newGame = CommandLineOption(s1, GameContainer.startNewGame())
  val continue = CommandLineOption(s2, GameContainer.navToSettings())
  val settings = CommandLineOption(s3, GameContainer.navToSettings())
  val quit = CommandLineOption(s4, GameContainer.navToSettings())

  val optnBoardLen = CommandLineOption("Set Board Length", GameContainer.setGameBoardLength())
  val optnPlayer2 = CommandLineOption("Set Player 2 difficulty", GameContainer.navToSettings())
  val optnEraseSavedGame = CommandLineOption("Erase Saved Game", GameContainer.navToSettings())
  val optnBackMain = CommandLineOption("Back to Main Menu", GameContainer.navToMainMenu())

  val mainWithSavedGame: ListMap[Int, CommandLineOption] =
    ListMap[Int, CommandLineOption](
      1 -> continue,
      2 -> newGame,
      3 -> settings,
      0 -> quit
    )

  val mainWithoutSavedGame: ListMap[Int, CommandLineOption] =
    ListMap[Int, CommandLineOption](
      1 -> newGame,
      2 -> settings,
      0 -> quit
    )

  val settingsMenu: ListMap[Int, CommandLineOption] =
    ListMap[Int, CommandLineOption](
      1 -> optnBoardLen,
      2 -> optnPlayer2,
      3 -> optnEraseSavedGame,
      4 -> optnBackMain,
      0 -> quit
    )
    
}
