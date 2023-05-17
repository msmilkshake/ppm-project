package io

import core.Board.Board
import core.Cells.{Blue, Cell, Red}
import core.Coord.Coord
import core.ProgramState.{Exit, GameRunning, InMainMenu, ProgramState, SaveGame, UndoMove}
import core.{Difficulty, Settings}
import io.StringUtils.{blueString, boldString, greenString, redString}
import tui.{CommandLineOption, Container}

import java.io.File
import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

object IOUtils {

  val saveFolderPath = "saves/"
  val continuePath = f"last/continue"

  def displaySaveSuccess(saveName: String): Unit = {
    greenString(f"Saved game with name: $saveName successfully.\n")
  }
  
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

  def displayWinner(cell: Cell): Unit = {
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
  }

  def promptEndGameOption(): ProgramState = {
    println(f"Press ${boldString("Enter")} for ${boldString("Main Menu")} " +
      f"or ${boldString("Q")} to ${boldString("Quit the Game")}")

    getUserInput() match {
      case "Q" => Exit
      case _ => InMainMenu
    }
  }

  def displayNewGameSettings(s: Settings): Unit = {
    val difficulty = s.difficulty match {
      case Difficulty.Easy => "Easy"
      case Difficulty.Medium => "Medium"
    }
    println(boldString("-- New Game Settings --"))
    println(f"${redString("Board length:")} ${blueString(f"${s.boardLength}")}")
    println(f"${redString("Computer difficulty:")} ${blueString(difficulty)}\n")
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

  def prompt(msg: String): String = {
    print(f"$msg: ")
    readLine.trim.toUpperCase
  }

  def promptRaw(msg: String): String = {
    print(f"$msg: ")
    readLine.trim
  }
  
  def promtSaveFilename(): String = {
    val filename =
      IOUtils.promptRaw("Enter the save filename (alphanumeric characters only)")
        
    // The regex matches alphanumeric words one or more times,
    // and then spaces or words zero or more times.
    if (!filename.matches("\\w+[\\s\\w]*")) {
      println(redString("Invalid filename.\n"))
      promtSaveFilename()
    } else {
      filename
    }
  }
  
  def promptLoadGame(): String = {
    IOUtils.promptRaw("Enter the save filename or " +
      "leave blank to go back") match {
      case s if s.isBlank || checkSaveExists(s) => s
      case _ =>
        println(redString("Invalid save file.\n"))
        promptLoadGame()
    }
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
  
  def checkHasContinue(): Boolean = {
    checkSaveExists(continuePath)
  }

  def checkSaveExists(file: String): Boolean = {
    val saveFile = new File(f"$saveFolderPath$file.sav")
    saveFile.exists()
  }

  def deleteContinueFile(): Unit = {
    deleteSaveFile(continuePath)
  }
  
  def deleteSaveFile(file: String): Unit = {
    val saveFile = new File(f"$saveFolderPath$file.sav")
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
    new File(f"$saveFolderPath$continuePath.sav").delete()
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
      case core.Cells.Red => redString("Player 1")
      case core.Cells.Blue => blueString("Player 2")
    }
    val input = prompt(f"$player Enter ${boldString("Coordinates")} " +
      f"(format: ${boldString("'row'")} <space> ${boldString("'col'")}),\n" +
      f"Or enter ${boldString("'M'")} to go to the ${boldString("Main Menu")},\n" +
      f"Or ${boldString("'U'")} to ${boldString("Undo")} the last move,\n" +
      f"Or ${boldString("'S'")} to ${boldString("Save Game")}.")
    println()
    input match {
      case "M" => (None, InMainMenu)
      case "U" => (None, UndoMove)
      case "S" => (None, SaveGame)
      case string =>
        validateCoords(string, board.length) match {
          case None => actionPrompt(cell, board)
          case Some(coord) => (Some(coord), GameRunning)
        }
    }
  }

}
