package IO

import Core.Board.Board
import TUI.{CommandLineOption, Container}

import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

object IOUtils {
  def printCoordsOccupied(): Unit = {
    println("Cell already occupied")
  }

  def printCoordsOutBounds():Unit= {
    println("Coordinates out of bounds")
  }

  def getUserInputInt(msg: String): Try[Int] = {
    print(msg + ": ")
    Try(readLine.trim.toUpperCase.toInt)
  }

  def getBoardLen(msg: String): Int = {
    getUserInputInt(msg) match {
      case Success(v) => v
      case Failure(_) => getBoardLen(msg)
    }
  }

  def prompt(msg: String): String = {
    print(msg + ": ")
    scala.io.StdIn.readLine()
  }

  def promptCoords(board: Board):(Int,Int) ={
    BoardPrinter.printBoard(board)
    val input = prompt("Enter coordinates")
    val split = input.split("\\s*,\\s*")
    split match {
      case Array(row, col) => Try((row.toInt, col.toInt)) match {
        case Success((r, c)) => (r-1, c-1)
        case _ => println("Wrong Input")
          promptCoords(board)
      }
      case _ => println("Wrong Input")
        promptCoords(board)
    }
  }

  def optionPrompt(title: String, options: Map[Int, CommandLineOption]): Option[CommandLineOption] = {
    println(f"-- $title --")
    options.toList map
      ((option: (Int, CommandLineOption)) => println(option._1 + ") " + option._2.name))

    getUserInputInt("Select an option") match {
      case Success(i) => options.get(i)
      case Failure(_) => println("Invalid number!"); optionPrompt(title, options)
    }
  }

}
