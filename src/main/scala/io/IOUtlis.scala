package io

import com.sun.net.httpserver.Authenticator.Success
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
      case Success(i) => options.get(i)
      case Failure(_) => println("Invalid number!"); optionPrompt(options)
    }
  }

}
