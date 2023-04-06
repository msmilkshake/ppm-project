package tui

import logic.Difficulty

import scala.collection.immutable.ListMap

object Menu {
  
  val labels = (
    "Start New Game",                             //  1
    "Resume Saved Game",                          //  2
    "Game Settings",                              //  3
    "Quit",                                       //  4
    "Set Board Length",                           //  5
    "Set Computer Difficulty",                    //  6
    "Erase Saved Game",                           //  7
    "Easy Computer",                              //  8
    "Medium Computer",                            //  9
    "Back to Main Menu",                          // 10
    "Back to Settings",                           // 11
    "Show Current Settings for New Games",        // 12
  )
  

  // -- Main Menu entries --
  val mainNewGame = CommandLineOption(labels._1, Container.startNewGame())
  val mainContinue = CommandLineOption(labels._2, Container.navToSettings())
  val showCurrSettings = CommandLineOption(labels._12, Container.showCurrSettings())

  // -- Settings entries --
  val settingsBoardLen = CommandLineOption(labels._5, Container.setGameBoardLength())
  val settingsDifficulty = CommandLineOption(labels._6, Container.navToSetPlayer2Type())
  val settingsEraseSavedGame = CommandLineOption(labels._7, Container.deleteSavedGame())

  // -- Player 2 Setup entries --
  val settingDifficultyEasy =
    CommandLineOption(labels._8, Container.setDifficulty(Difficulty.Easy))
  val settingDifficultyMedium =
    CommandLineOption(labels._9, Container.setDifficulty(Difficulty.Medium))
  
  // -- Navigation entries --
  val navMain = CommandLineOption(labels._10, Container.navToMainMenu())
  val navSettings = CommandLineOption(labels._3, Container.navToSettings())
  val navBackSettings = CommandLineOption(labels._11, Container.navToSettings())
  val navQuit = CommandLineOption(labels._4, Container.exitProgram())


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
      2 -> settingsDifficulty,
      3 -> settingsEraseSavedGame,
      4 -> navMain,
      0 -> navQuit
    )
    
  val setPlayer2Type: ListMap[Int, CommandLineOption] =
    ListMap[Int, CommandLineOption](
      1 -> settingDifficultyEasy,
      2 -> settingDifficultyMedium,
      3 -> navBackSettings
    )
    
}
