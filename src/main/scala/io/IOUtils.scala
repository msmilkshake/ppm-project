package io

import io.StringUtils.redString
import ui.tui.CommandLineOption

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

object IOUtlis {

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
  def optionPrompt(options: Map[Int, CommandLineOption]): Option[CommandLineOption] = {
    println("-- Options --")
    options.toList map
      ((option: (Int, CommandLineOption)) => println(option._1 + ") " + option._2.name))

    getUserInputInt("Select an option") match {
      case Success(i) => println(); options.get(i)
      case Failure(_) => println(redString("Invalid number!\n")); optionPrompt(options)
    }
  }
  
  def promptBoardLen(): Int = {
    numberPrompt("Enter the desired board length:") match {
      case n if n > 4 && n < 100 =>
        println("The size must be between 4 and 99!\n")
        promptBoardLen()
      case n => n
    }
    
  }

  def warningInvalidOption(): Unit = {
    println(redString("Invalid option!\n"))
  }

}
