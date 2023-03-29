package io

import logic.Cells.{Blue, Board, Red}

import scala.annotation.tailrec

object IOUtils {
  val blueColor = "\u001b[0;34m"
  val redColor = "\u001b[0;31m"
  val resetColor = "\u001b[0m"

  def blueString(s: String): String = {
    f"${blueColor}${s}${resetColor}"
  }
  
  def redString(s: String): String = {
    f"${redColor}${s}${resetColor}"
  }
  def printNum(n: Int): Unit = {
    print(n)
  }
  
  def printBoardHeader(n: Int) = {
    val lblRowOffset = 5
    val hexEdgeOffset = 7
    
    @tailrec
    def printNumLabels(i: Int, n: Int): Unit = {
      if (i <= n) {
        print(" ".repeat(3) + i)
        printNumLabels(i + 1, n)
      }
    }
    
    def printGridLine(i: Int, n: Int): Unit = {
      def getBoardElement(row: Int, col: Int, board: Board): String = {
        board(row)(col) match {
          case Red => redString("X")
          case Blue => blueString("O")
          case _ => " "
        }
      }
      
      def printSingleCell(row: Int, col: Int, board: Board): Unit = {
        print(f" ${getBoardElement(row, col, board)} |")
      }
      
      if (i <= n) {
        print(" ".repeat(i * 2 - 1) + f"${i}  ${redString("*")}  |")
      }
    }
    
    print(" ".repeat(lblRowOffset))
    printNumLabels(1, n)
    println()
    println(blueString("         *" + " * *".repeat(n - 1)))
    print(" ".repeat(hexEdgeOffset))
    print(" ╱ ╲".repeat(n))
    println()
    printGridLine(1, n)
    
  }

}
