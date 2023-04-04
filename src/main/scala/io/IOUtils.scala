package io

import io.StringUtils.{blueString, boldString, boldText, greenString, redString, resetColor}
import logic.Cells.Cell
import logic.{GameState, PlayerType}
import ui.tui.{CommandLineOption, GameContainer}

import java.io.File
import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

object IOUtils {
  def displayComputerPlay(row: Int, col: Int): Unit = {
    println(f"${blueString("Computer")} played at coords: ${greenString(f"${row} $col")}.")
  }

  def saveState(c: GameContainer) = {
    
  }
  
  val saveFilePath = "states/savefile"
  def displayCurrentSettings(gs: GameState): Unit = {
    val p2 = gs.players._2 match {
      case PlayerType.Easy => "Easy"
      case PlayerType.Medium => "Medium"
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
    saveFile.exists() match {
      case true => saveFile.delete() match {
        case true => println("Save file deleted successfully!\n")
        case false => println("Could not delete the save file.\n")
      }
      case false => println("There's no save file to delete.\n")
    }
  }

  @tailrec
  def promptCoords(cell: Cell, len: Int): (Int, Int) = {
    val player = cell match {
      case logic.Cells.Red => redString("Player 1")
      case logic.Cells.Blue => blueString("Player 2")
    }
    val coords = 
      coordPrompt(f"$player Enter Coordinates (row <space> col), or 'M' to go to the Main Menu")
    coords match {
      case (-1, -1) => (-1, -1)
      case (row, col) =>
        if (row < 1 || row > len || col < 1 || col > len) {
          println("The coordinates are out of range.\n")
          promptCoords(cell, len)
        } else {
          println()
          (row, col)
        }
    }
  }

}
