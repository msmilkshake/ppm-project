package Core

import Core.Board.Board
import Core.Cells.{Cell, Empty, Red, Blue}
import Core.Coord.Coord
import IO.IOUtils
import TUI.Container


object GameCore {

  //T1
  def randomMove(board: Board, rand: MyRandom): (Coord, MyRandom) = {
    //lista celulas vazias validas para jogar
    val validCells = filterCells(board, Empty)
    // escolher um número entre 0 e o máx da lista (será o indice)
    val (n, nextRnd) = rand.nextInt(validCells.length)
    //posição aleatória da lista de celulas vazias, nextRandom
    (validCells(n), nextRnd.asInstanceOf[MyRandom])
  }

  //T2
  def play(board: Board, player: Cells.Cell, coord: Coord): Board = {
    //obter uma lista de tuplos (lista(cell),index da linha)
    board.zipWithIndex.map {
      // se o index da linha igual a row
      case (lstCell, rowIndex) => if (rowIndex == coord._1) {
        //obter uma lista de tuplos (cell, index da coluna)
        lstCell.zipWithIndex.map {
          //se index da coluna igual a col, criar novo board com a cell na posição (row,col) igual a player
          case (cell, index) => if (index == coord._2) player else cell
        }
      } else lstCell
    }
  }

  def playerMove(gs: GameState, coord: Coord): GameState = {
    val newBoard = play(gs.board, Red, coord)
    GameState(newBoard, gs.rnd, gs.winner)
  }

  def computerMove(gs: GameState): GameState = {
    val (coord, newRnd) = randomMove(gs.board, gs.rnd)
    val newBoard = play(gs.board, Blue, coord)
    GameState(newBoard, newRnd, gs.winner)
  }

  def playTurn(c: Container): Container = {
    val coords = IOUtils.promptCoords(c.gs.board)
    coords match {
      case (row, col) if (row < 0 || row >= c.gs.board.length ||
        col < 0 || col >= c.gs.board.length) =>
        IOUtils.printCoordsOutBounds()
        c
      case (row, col) if (c.gs.board(row)(col) != Empty) =>
        IOUtils.printCoordsOccupied()
        c
      case _ =>
        val newState = playerMove(c.gs, coords)
        val newState2 = computerMove(newState)
        Container(newState2, c.history, c.ps, c.len)
    }

  }


  def filterCells(board: Board, cell: Cell): List[Coord] = {
    board.zipWithIndex.flatMap {
      case (subList, row) => subList.zipWithIndex.collect {
        case (cell1, col) if cell1 equals cell => (row, col)
      }
    }
  }

}
