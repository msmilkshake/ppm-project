package tui

import scala.collection.immutable.ListMap

object Menu {
  val cmdStartGame = CommandLineOption("Start New Game",Container.startNewGame())
  val cmdSettings = CommandLineOption("Game Settings", Container.settings())
  val cmdChangeBoardLen = CommandLineOption("Change Board Lenght", Container.setBoardLen())
  val cmdBackToMain = CommandLineOption("Back to Main", Container.backToMain())
  val cmdExit = CommandLineOption("Exit", Container.exit())

  val mainMenu :ListMap[Int,CommandLineOption] = ListMap(
    1 -> cmdStartGame,
    2 -> cmdSettings,
    0 -> cmdExit
  )

  val settingsMenu : ListMap[Int,CommandLineOption] = ListMap(
    1 -> cmdChangeBoardLen,
    2 -> cmdBackToMain,
    0 -> cmdExit
  )


}
