package ui.tui

import logic.{GameState, PlayerType}

import scala.collection.immutable.ListMap

object Menu {
  
  val labels = (
    "Start New Game",                           //  1
    "Continue Last Game",                       //  2
    "Game Settings",                            //  3
    "Quit",                                     //  4
    "Set Board Length",                         //  5
    "Set Player 2 (Computer / Human)",          //  6
    "Set Who Plays First",                      //  7
    "Erase Saved Game",                         //  8
    "Easy Computer",                            //  9
    "Medium Computer",                          // 10
    "Another Person (Keyboard Share)",          // 11
    "Back to Main Menu",                        // 12
    "Back to Settings",                         // 13
    "Player 1",                                 // 14
    "Player 2",                                 // 15
    "Make it Random!",                          // 16
    "Show Current Settings for New Games",      // 17
  )
  

  // -- Main Menu entries --
  val mainNewGame = CommandLineOption(labels._1, GameContainer.startNewGame())
  val mainContinue = CommandLineOption(labels._2, GameContainer.navToSettings())

  // -- Settings entries --
  val settingsBoardLen = CommandLineOption(labels._5, GameContainer.setGameBoardLength())
  val settingsPlayer2 = CommandLineOption(labels._6, GameContainer.navToSetPlayer2Type())
  val settingsWhoPlaysFirst = CommandLineOption(labels._7, GameContainer.navToSetWhoPlaysFirst())
  val settingsEraseSavedGame = CommandLineOption(labels._8, GameContainer.deleteSavedGame())

  // -- Player 2 Setup entries --
  val settingPlayer2Easy =
    CommandLineOption(labels._9, GameContainer.setPlayer2(PlayerType.Easy))
  val settingPlayer2Medium =
    CommandLineOption(labels._10, GameContainer.setPlayer2(PlayerType.Medium))
  val settingPlayer2Human = {
    CommandLineOption(labels._11, GameContainer.setPlayer2(PlayerType.Human))
  }

  // -- Who Plays First entries --
  val settingPlayer1Starts =
    CommandLineOption(labels._14, GameContainer.setStartingPlayer(1))
  val settingPlayer2Starts =
    CommandLineOption(labels._15, GameContainer.setStartingPlayer(2))
  val settingRandomizeStarter =
    CommandLineOption(labels._16, GameContainer.setStartingPlayer(0))
  
  // -- Navigation entries --
  val navMain = CommandLineOption(labels._12, GameContainer.navToMainMenu())
  val navSettings = CommandLineOption(labels._3, GameContainer.navToSettings())
  val navBackSettings = CommandLineOption(labels._13, GameContainer.navToSettings())
  val navQuit = CommandLineOption(labels._4, GameContainer.exitProgram())

  val showCurrSettings = CommandLineOption(labels._17, GameContainer.showCurrSettings())

  val mainWithSavedGame: ListMap[Int, CommandLineOption] =
    ListMap[Int, CommandLineOption](
      1 -> mainContinue,
      2 -> mainNewGame,
      3 -> navSettings,
      4 -> showCurrSettings,
      0 -> navQuit
    )

  val mainWithoutSavedGame: ListMap[Int, CommandLineOption] =
    ListMap[Int, CommandLineOption](
      1 -> mainNewGame,
      2 -> navSettings,
      3 -> showCurrSettings,
      0 -> navQuit
    )

  val settingsMenu: ListMap[Int, CommandLineOption] =
    ListMap[Int, CommandLineOption](
      1 -> settingsBoardLen,
      2 -> settingsPlayer2,
      3 -> settingsWhoPlaysFirst,
      4 -> settingsEraseSavedGame,
      5 -> navMain,
      0 -> navQuit
    )
    
  val setPlayer2Type: ListMap[Int, CommandLineOption] =
    ListMap[Int, CommandLineOption](
      1 -> settingPlayer2Easy,
      2 -> settingPlayer2Medium,
      3 -> settingPlayer2Human,
      4 -> navBackSettings
    )

  val setWhoPlaysFirst: ListMap[Int, CommandLineOption] =
    ListMap[Int, CommandLineOption](
      1 -> settingPlayer1Starts,
      2 -> settingPlayer2Starts,
      3 -> settingRandomizeStarter,
      4 -> navBackSettings
    )
}
