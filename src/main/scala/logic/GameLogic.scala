package logic


import io.BoardPrinter.printBoard
import io.IOUtils
import logic.Cells.{Blue, Board, Cell, Empty, Red}
import logic.PlayerType.PlayerType
import logic.ProgramState.MainMenu
import ui.tui.GameContainer

import scala.annotation.tailrec

object GameLogic {

  def playerMove(player: Cell, gs: GameState, playerType: PlayerType): GameState = {
      playerType match {
      case logic.PlayerType.Human => humanMove(player, gs)
      case logic.PlayerType.Easy => ???
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
  
  @tailrec
  def humanMove(cell: Cell, gs: GameState): GameState = {
    printBoard(gs.board)
    val (row, col) = IOUtils.promptCoords(cell, gs.boardLen)
    if (row == -1 && col == -1) {
      gs
    } else {
      gs.board(row - 1)(col - 1) match {
        case Empty =>
          val newBoard = play(gs.board, cell, row - 1, col - 1)
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
          humanMove(cell, gs)
      }
    }
  }
  
  def easyMove(cell: Cell, gs: GameState): GameState = {
    ???
  }

  def playTurn(c: GameContainer): GameContainer =  {
    val newState: GameState = c.gs.turn match {
      case 1 =>
        playerMove(Red,c.gs, c.gs.players._1)
      case 2 =>
        playerMove(Blue, c.gs, c.gs.players._2)
        
    }
    if (newState.turn == c.gs.turn) {
      IOUtils.saveState(c)
      GameContainer(c.gs, c.h, MainMenu)
    }
    GameContainer(newState, c.gs :: c.h, c.ps)
  }

  @tailrec
  def randomMove(board: Board, rand: MyRandom): ((Int, Int), MyRandom) = {
    val (row, newRand1) = rand.nextInt(board.length)
    val (col, newRand2) = newRand1.nextInt(board.length)
    board(row)(col) match {
      case logic.Cells.Empty => ((row, col), newRand2.asInstanceOf[MyRandom])
      case _ => randomMove(board, newRand2.asInstanceOf[MyRandom])
    }
  }
  
}
