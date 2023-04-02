package ui.tui

import scala.collection.SortedMap
import scala.collection.immutable.ListMap

object Menu {
  val newGame = CommandLineOption("Start New Game", _)
  val continue = CommandLineOption("Continue Last Game", _)
  val options = CommandLineOption("Game Options", _)
  val quit = CommandLineOption("Quit", _)
  
  
  val mainWithSavedGame = ListMap[Int, CommandLineOption](
    1 -> CommandLineOption("Continue Last Game", _),
    2 -> CommandLineOption("Start New Game", _),
    3 -> CommandLineOption("Game Options", _),
    0 -> CommandLineOption("Quit", _)
  )

  val mainWithoutSavedGame = ListMap[Int, CommandLineOption](
    1 -> CommandLineOption("Continue Last Game", _),
    2 -> CommandLineOption("Start New Game", _),
    3 -> CommandLineOption("Game Options", _),
    0 -> CommandLineOption("Quit", _)
  )
}
