package logic


import io.BoardPrinter.printBoard
import io.IOUtils
import logic.Cells.{Blue, Board, Cell, Empty, Red}
import logic.PlayerType.PlayerType
import logic.ProgramState.MainMenu
import ui.tui.GameContainer

import scala.annotation.tailrec

object GameLogic {

  def playerMove(gs: GameState): GameState = {
    printBoard(gs.board)
    val (row, col) = IOUtils.promptCoords(Red, gs.boardLen)
    if (row == -1 && col == -1) {
      gs
    } else {
      gs.board(row - 1)(col - 1) match {
        case Empty =>
          val newBoard = play(gs.board, Red, row - 1, col - 1)
          GameState(
            gs.boardLen,
            newBoard,
            gs.players,
            gs.random,
            gs.firstPlayer,
            gs.turn % 2 + 1,
            gs.isRandom,
            gs.saveExists
          )
        case _ =>
          IOUtils.warningOccupiedCell()
          playerMove(gs)
      }
    }
  }
  
  def computerMove(gs: GameState, computer: PlayerType): GameState = {
    computer match {
      case logic.PlayerType.Easy => easyMove(gs)
      case logic.PlayerType.Medium => ???
    }
  }

  def play(board: Board, player: Cell, row: Int, col: Int): Board = {
    board.zipWithIndex map {
      case (cells, i) => cells.zipWithIndex map {
        case (cell, j) => if (i == row && j == col) player else cell
      }
    }
  }

  def easyMove(gs: GameState): GameState = {
    val ((row, col), newRand) = randomMove(gs.board, gs.random)
    IOUtils.displayComputerPlay(row + 1, col + 1)
    GameState(
      gs.boardLen,
      play(gs.board, Blue, row, col),
      gs.players,
      newRand,
      gs.firstPlayer,
      gs.turn,
      gs.isRandom,
      gs.saveExists
    )
  }

  def playTurn(c: GameContainer): GameContainer = {
        val gs1 = playerMove(c.gs)
        val gs2 = computerMove(gs1, c.gs.players._2)
    GameContainer(gs2, c.gs :: c.h, c.ps)
  }
  
  def randomMove(board: Board, rand: MyRandom): ((Int, Int), MyRandom) = {
    val emptyCells = getEmptyCellCoords(board)
    val (target, newRand) = rand.nextInt(emptyCells.length)
    (emptyCells(target), newRand.asInstanceOf[MyRandom])
  }

  def getEmptyCellCoords(board: Board): List[(Int, Int)] = {
    (board.zipWithIndex foldRight List[(Int, Int)]())((line, acc) => {
      (line._1.zipWithIndex foldRight acc)((cell, result) => {
        val coord = (line._2, cell._2)
        if (cell._1 == Empty) coord :: result
        else result
      })
    })
  }

}
