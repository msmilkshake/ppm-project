package ui.tui

import scala.collection.SortedMap
import scala.collection.immutable.ListMap

object Menu {
  val newGame = CommandLineOption("Start New Game", _)
  val continue = CommandLineOption("Continue Last Game", _)
  val options = CommandLineOption("Game Options", _)
  val quit = CommandLineOption("Quit", _)
  
  
  val mainWithSavedGame = ListMap[Int, CommandLineOption](
    1 -> continue,
    2 -> newGame,
    3 -> options,
    0 -> quit
  )

  val mainWithoutSavedGame = ListMap[Int, CommandLineOption](
    1 -> newGame,
    2 -> options,
    0 -> quit
  )
}
