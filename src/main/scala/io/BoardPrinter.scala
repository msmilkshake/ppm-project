package io


import core.Board.Board
import core.Cells.{Cell, Red, Blue, Empty}
import io.StringUtils._

import scala.annotation.tailrec

object BoardPrinter {

  def printBoard(board: Board): Unit = {

    @tailrec
    def buildLabelsList(n: Int, lst: List[Int]): List[Int] = {
      n match {
        case 0 => lst
        case x => buildLabelsList(x - 1, x :: lst)
      }
    }

    val labels = buildLabelsList(board.length, Nil)

    printBoardHeader(labels)
    printBoardGrid(board, board.length, labels)
    printBoardFooter(board.length)
  }

  def printBoardHeader(labels: List[Int]): Unit = {
    val labelOffset = "     "
    val starOffset = blueString("         *")
    val starPattern = blueString(" * *")
    val hexOffset = "       "
    val hexPattern = " ╱ ╲"

    @tailrec
    def buildLabels(labels: List[Int], s: String): String = {
      labels match {
        case Nil => s
        case x :: xs =>
          val padding = x / 10 match {
            case 0 => " "
            case _ => ""
          }
          buildLabels(xs, f"$s  $padding$x")
      }
    }

    println(buildLabels(labels, labelOffset))
    println(f"$starOffset${starPattern * (labels.length - 1)}")
    println(f"$hexOffset${hexPattern * labels.length}")
  }

  @tailrec
  def printBoardGrid(board: Board, len: Int, labels: List[Int]): Unit = {

    val offset = "  "

    def buildgridLine(row: List[Cell], s: String): String = {

      @tailrec
      def buildCell(cells: List[Cell], s: String): String = {
        cells match {
          case Nil => s
          case c :: cs =>
            val cell = c match {
              case Red => redString("X")
              case Blue => blueString("O")
              case _ => " "
            }
            buildCell(cs, f"$s $cell |")
        }
      }

      f"${buildCell(row, s)}  ${redString("*")}"
    }

    @tailrec
    def buildHexSep(n: Int, s: String): String = {
      n match {
        case 0 => f"$s  ${redString("*")}"
        case _ => buildHexSep(n - 1, f"$s / \\")
      }
    }

    (board, labels) match {
      case (x :: Nil, n :: Nil) =>
        val padding = n / 10 match {
          case 0 => " "
          case _ => ""
        }
        println(buildgridLine(x, f"$padding${offset * (n - 1)}$n  ${redString("*")}  |"))

      case (x :: xs, n :: ns) =>
        val padding = n / 10 match {
          case 0 => " "
          case _ => ""
        }
        println(buildgridLine(x, f"$padding${offset * (n - 1)}$n  ${redString("*")}  |") +
          "\n" + buildHexSep(len, f"   ${offset * n}${redString("*")}  \\"))
        printBoardGrid(xs, len, ns)

    }

  }

  def printBoardFooter(n: Int): Unit = {
    val offset = "      "
    val hexPattern = " / \\"
    val starPattern = blueString(" * *")
    println(f"$offset${"  " * n}\\${hexPattern * (n - 1)} /")
    println(f"$offset${"  " * n}${starPattern * (n - 1)} ${blueString("*")}")
  }

}
