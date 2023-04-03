package io

import io.StringUtils.{blueString, redString}
import logic.{GameState, PlayerType}
import sun.awt.shell.ShellFolder
import ui.tui.{CommandLineOption, GameContainer}

import java.io.File
import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

object IOUtils {
  
  val saveFilePath = "states/savefile"
  def displayCurrentSettings(gs: GameState): Unit = {
    val p2 = gs.players._2 match {
      case PlayerType.Easy => "Easy Computer"
      case PlayerType.Medium => "Medium Computer"
      case PlayerType.Human => "Human Player (Shared Keyboard)"
    }
    
    val first = gs.firstPlayer match {
      case 1 => "Player 1"
      case 2 => "Player 2"
      case _ => "Random"
    }
    println("-- Current Settings --")
    println(f"${redString("Board length:")} ${blueString(f"${gs.boardLen}")}")
    println(f"${redString("Player 2 type:")} ${blueString(p2)}")
    println(f"${redString("Who plays first:")} ${blueString(first)}\n")
  }


  def getUserInput(): String = readLine.trim.toUpperCase

  def getUserInputInt(msg: String): Try[Int] = {
    print(msg + ": ")
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
    print(msg + ": ")
    scala.io.StdIn.readLine()
  }

  @tailrec
  def optionPrompt(title: String, options: Map[Int, CommandLineOption]): Option[CommandLineOption] = {
    println(f"-- ${title} --")
    options.toList map
      ((option: (Int, CommandLineOption)) => println(option._1 + ") " + option._2.name))

    getUserInputInt("Select an option") match {
      case Success(i) => println(); options.get(i)
      case Failure(_) => println(redString("Invalid number!\n")); optionPrompt(title, options)
    }
  }
  
  def promptBoardLen(): Int = {
    numberPrompt("Enter the desired board length") match {
      case n if n < 4 || n > 99 =>
        println("The size must be between 4 and 99!\n")
        promptBoardLen()
      case n => n
    }
    
  }

  def warningInvalidOption(): Unit = {
    println(redString("Invalid option!\n"))
  }
  
  def checkSaveExists(): Boolean = {
    val saveFile = new File(saveFilePath)
    saveFile.exists()
  }

  def deleteSaveFile(): Unit = {
    val saveFile = new File(saveFilePath)
    saveFile.exists() match {
      case true => saveFile.delete()
    }
  }

}
