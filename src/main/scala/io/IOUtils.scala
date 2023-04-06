package io

import io.StringUtils.{blueString, boldString, greenString, redString}
import logic.Board.Board
import logic.Cells.{Blue, Cell, Red}
import logic.Coord.Coord
import logic.ProgramState.{Exit, GameRunning, MainMenu, ProgramState, Undo}
import logic.{Difficulty, GameState}
import tui.{CommandLineOption, Container}

import java.io.File
import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

object IOUtils {

  val saveFilePath = "states/savefile"
  def displayUndoSuccess(): Unit =
    println(greenString("Undid the last move successfully."))

  def displayNoUndoMoves(): Unit = 
    println(redString("There are no moves to Undo.\n"))

  def displayComputerPlay(row: Int, col: Int): Unit = {
    println(f"${blueString("Computer")} played at coords: ${greenString(f"$row $col")}.")
  }
  
  def displayGoodbyeMessage(): Unit = {
    println("Goodbye! \uD83D\uDC4B")
  }

  def displayWinner(cell: Cell): ProgramState = {
    val border = greenString("* = = = = = = = = = = = = = = = = *")
    val s1 = greenString(" \\\\  ~     ~     ~     ~     ~  //")
    val s2 = greenString(" //  ~     ~     ~     ~     ~  \\\\")
    val msg = cell match {
      case Red =>
        f"${greenString(" ||      ")}" +
          f"${redString(boldString("# Player  WINS! #"))}" +
          f"${greenString("      ||")}"
      case Blue =>
        f"${greenString(" ||     ")}" +
          f"${blueString(boldString("# Computer  WINS! #"))}" +
          f"${greenString("     ||")}"
    }
    
    println(f"\n$border\n$s1\n$msg\n$s2\n$border")
    println(f"Press ${boldString("Enter")} for ${boldString("Main Menu")} " +
      f"or ${boldString("Q")} to ${boldString("Quit the Game")}")
    
    getUserInput() match {
      case "Q" => Exit
      case _ => MainMenu
    }
  }

  def saveState(c: Container) = {

  }

  def displayCurrentSettings(gs: GameState): Unit = {
    val p2 = gs.computerDifficulty match {
      case Difficulty.Easy => "Easy"
      case Difficulty.Medium => "Medium"
    }
    println(boldString("-- Current Settings --"))
    println(f"${redString("Board length:")} ${blueString(f"${gs.boardLen}")}")
    println(f"${redString("Computer difficulty:")} ${blueString(p2)}\n")
  }


  def getUserInput(): String = readLine.trim.toUpperCase

  def getUserInputInt(msg: String): Try[Int] = {
    print(f"$msg: ")
    Try(readLine.trim.toUpperCase.toInt)
  }

  @tailrec
  def numberPrompt(msg: String): Int = {
    getUserInputInt(msg) match {
      case Success(i) => println(); i
      case Failure(_) => println(redString("Invalid number!\n")); numberPrompt(msg)
    }
  }

  @tailrec
  def coordPrompt(msg: String): (Int, Int) = {
    prompt(msg).split("\\s+") match {
      case Array("M") => (-1, -1)
      case Array(row, col) =>
        Try((row.toInt, col.toInt)) match {
          case Success((r, c)) => (r, c)
          case Failure(_) =>
            println(redString("Invalid coordinates!\n"))
            coordPrompt(msg)
        }
      case _ =>
        println(redString("Invalid coordinates!\n"))
        coordPrompt(msg)
    }
  }

  def prompt(msg: String): String = {
    print(f"$msg: ")
    readLine.trim.toUpperCase
  }

  @tailrec
  def optionPrompt(title: String, options: Map[Int, CommandLineOption]): Option[CommandLineOption] = {
    println(greenString(boldString(f"-- $title --")))
    options.toList map ((option: (Int, CommandLineOption)) =>
      println(f"${boldString(f"${option._1})")} ${option._2.name}"))

    getUserInputInt("Select an option") match {
      case Success(i) => println(); options.get(i)
      case Failure(_) => println(redString("Invalid number!\n")); optionPrompt(title, options)
    }
  }

  @tailrec
  def promptBoardLen(): Int = {
    numberPrompt("Enter the desired board length") match {
      case n if n < 3 || n > 99 =>
        println("The size must be between 3 and 99!\n")
        promptBoardLen()
      case n => n
    }
  }

  def warningInvalidOption(): Unit = {
    println(redString("Invalid option!\n"))
  }

  def warningOccupiedCell(): Unit = {
    println(redString("Cell is already occupied!\n"))
  }

  def checkSaveExists(): Boolean = {
    val saveFile = new File(saveFilePath)
    saveFile.exists()
  }

  def deleteSaveFile(): Unit = {
    val saveFile = new File(saveFilePath)
    if (saveFile.exists()) {
      if (saveFile.delete()) {
        println("Save file deleted successfully!\n")
      } else {
        println("Could not delete the save file.\n")
      }
    } else {
      println("There's no save file to delete.\n")
    }
  }
  
  def deleteFileQuiet(): Unit = {
    new File(saveFilePath).delete()
  }

  def validateCoords(coord: String, len: Int): Option[Coord] = {
    coord.split("\\s+") match {
      case Array(row, col) =>
        Try((row.toInt, col.toInt)) match {
          case Success((r, c)) =>
            if (r < 1 || r > len || c < 1 || c > len) {
              println(redString("The coordinates are out of range.\n"))
              None
            } else {
              Some((r, c))
            }
          case Failure(_) =>
            println(redString("Invalid coordinates!\n"))
            None
        }
      case _ =>
        println(redString("Invalid coordinates!\n"))
        None
    }
  }

  @tailrec
  def actionPrompt(cell: Cell, board: Board): (Option[Coord], ProgramState) = {
    BoardPrinter.printBoard(board)
    val player = cell match {
      case logic.Cells.Red => redString("Player 1")
      case logic.Cells.Blue => blueString("Player 2")
    }
    val input = prompt(f"$player Enter ${boldString("Coordinates")} " +
      f"(format: ${boldString("'row'")} <space> ${boldString("'col'")}),\n" +
      f"Or enter ${boldString("'M'")} to go to the ${boldString("Main Menu")},\n" +
      f"Or ${boldString("'U'")} to ${boldString("Undo")} the last move")
    println()
    input match {
      case "M" => (None, MainMenu)
      case "U" => (None, Undo)
      case string =>
        validateCoords(string, board.length) match {
          case None => actionPrompt(cell, board)
          case Some(coord) => (Some(coord), GameRunning)
        }
    }
  }

}
