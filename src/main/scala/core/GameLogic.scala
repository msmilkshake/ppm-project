package core

import core.Board.Board
import core.Cells.{Blue, Cell, Empty, Red}
import core.Coord.Coord
import core.Difficulty.{Easy, Medium}
import core.ProgramState.{GameWon, ProgramState}
import io.IOUtils
import tui.Container

import scala.annotation.tailrec


object GameLogic {

  type MoveFunction = GameState => (Option[Coord], Option[ProgramState], RandomWithState)

  val adjacency: List[Coord] = List(
    (-1, 0), // top-left
    (-1, 1), // top-right
    (0, 1), // right
    (1, 0), // bottom-right
    (1, -1), // bottom-left
    (0, -1), // left
  )

  def doMove(gs: GameState, moveFunction: MoveFunction):
  (Option[Coord], Option[ProgramState], RandomWithState) = {
    moveFunction(gs)
  }

  def playerMove(gs: GameState): (Option[Coord], Option[ProgramState], RandomWithState) = {
    IOUtils.actionPrompt(Red, gs.board.get) match {
      case (None, state) => (None, Some(state), gs.random)
      case (Some((row, col)), _) =>
        gs.board.get(row - 1)(col - 1) match {
          case Empty =>
            (Some(row - 1, col - 1), None, gs.random)
          case _ =>
            IOUtils.warningOccupiedCell()
            playerMove(gs)
        }
    }
  }

  def easyMove(gs: GameState): (Option[Coord], Option[ProgramState], RandomWithState) = {
    val move = randomMove(gs.board.get, gs.random)
    (move._1, None, move._2)
  }

  def mediumMove(gs: GameState): (Option[Coord], Option[ProgramState], RandomWithState) = {
    getAllCells(gs.board.get, Blue) match {
      case Nil => easyMove(gs)
      case list =>
        val validPositions = list filter (coord => getAdjacents(gs.board.get, Empty, coord).nonEmpty)
        validPositions match {
          case Nil => easyMove(gs)
          case positions =>
            val (index, newRand) = gs.random.nextInt(validPositions.length)
            val candidates = getAdjacents(gs.board.get, Empty, positions(index))
            val (playIndex, newRand2) = newRand.nextInt(candidates.length)
            val (row, col) = candidates(playIndex)
            (Some((row, col)), None, newRand2)
        }
    }
  }

  def computerMove(gs: GameState): MoveFunction = {
    gs.difficulty match {
      case Easy => easyMove
      case Medium => mediumMove
    }
  }

  def setBoardCell(board: Board, player: Cell, row: Int, col: Int): Board = {
    board.zipWithIndex map {
      case (cells, i) => cells.zipWithIndex map {
        case (cell, j) => if (i == row && j == col) player else cell
      }
    }
  }

  def playTurn(c: Container): Container = {
    doMove(c.gameState, playerMove) match {
      case (None, Some(state), _) =>
        Container(c.gameState,
          c.playHistory,
          state,
          c.newGameSettings)
      case (Some((pRow, pCol)), _, _) =>
        val gs1 = GameState(
          Some(setBoardCell(c.gameState.board.get, Red, pRow, pCol)),
          c.gameState.difficulty,
          c.gameState.random,
          c.gameState.winner)

        hasContiguousLine(gs1.board.get, Red) match {
          case true =>
            return Container(
              GameState(gs1.board,
                gs1.difficulty,
                gs1.random,
                Some(Red)),
              c.playHistory,
              GameWon,
              c.newGameSettings)

          case _ => // Do Nothing
        }

        val (Some((cRow, cCol)), _, rand) = doMove(gs1, computerMove(gs1))
        val gs2 = GameState(
          Some(setBoardCell(gs1.board.get, Blue, cRow, cCol)),
          gs1.difficulty,
          rand,
          gs1.winner)
        IOUtils.displayComputerPlay(cRow + 1, cCol + 1)
        hasContiguousLine(gs2.board.get, Blue) match {
          case true => Container(
            GameState(gs2.board,
              gs2.difficulty,
              gs2.random,
              Some(Blue)),
            c.playHistory,
            GameWon,
            c.newGameSettings
          )
          case _ => Container(
            gs2,
            (cRow, cCol) :: (pRow, pCol) :: c.playHistory,
            c.programState,
            c.newGameSettings)
        }
    }
  }

  def randomMove(board: Board, rand: RandomWithState): (Some[Coord], RandomWithState) = {
    val emptyCells = getAllCells(board, Empty)
    val (target, newRand) = rand.nextInt(emptyCells.length)
    (Some(emptyCells(target)), newRand)
  }

  def getAllCells(board: Board, cell: Cell): List[Coord] = {
    (board.zipWithIndex foldRight List[Coord]())((row, acc) => {
      (row._1.zipWithIndex foldRight acc)((col, result) => {
        if (col._1 == cell) (row._2, col._2) :: result
        else result
      })
    })
  }

  def hasContiguousLine(b: Board, c: Cell): Boolean = {

    @tailrec
    def checkBorderPositions(b: Board, c: Cell, i: Int, win: Boolean): Boolean = {
      win match {
        case true => true
        case _ =>
          i match {
            case 0 => false
            case _ =>
              val isWinPath = c match {
                case Red =>
                  checkBorderCellAdjacents(b, c, List((b.length - i, 0)), Set())
                case Blue =>
                  checkBorderCellAdjacents(b, c, List((0, b.length - i)), Set())
              }
              checkBorderPositions(b, c, i - 1, isWinPath)
          }
      }
      
    }

    @tailrec
    def checkBorderCellAdjacents(b: Board, c: Cell, toCheck: List[Coord],
                                 set: Set[Coord]): Boolean = {
      toCheck match {
        case Nil => false
        case (row, col) :: remainingToCheck if b(row)(col) == c && !set.contains(row, col) =>
          c match {
            case Red if col == b.length - 1 => true
            case Blue if row == b.length - 1 => true
            case _ =>
              val uncheckedAdjacents = (getAdjacents(b, c, (row, col))
                foldLeft List[Coord]())((list, coord) =>
                if (!set.contains(coord)) {
                  coord :: list
                } else {
                  list
                })
              val coord = (row, col)
              checkBorderCellAdjacents(b, c, remainingToCheck ++ uncheckedAdjacents, set + coord)
          }
        case _ :: toCheckTail =>
          checkBorderCellAdjacents(b, c, toCheckTail, set)
      }
    }

    checkBorderPositions(b, c, b.length, false)
  }

  def getAdjacents(board: Board, cell: Cell, pos: Coord): List[Coord] = {

    @tailrec
    def adjacents(b: Board, c: Cell, p: Coord,
                  adj: List[Coord], res: List[Coord]): List[Coord] = {
      adj match {
        case Nil => res
        case (rowShift, colShift) :: tail =>
          val (row, col) = (p._1 + rowShift, p._2 + colShift)
          if (row < 0 || row >= board.length || col < 0 || col >= board.length) {
            adjacents(b, c, p, tail, res)
          } else {
            board(row)(col) match {
              case s if s == c => adjacents(b, c, p, tail, (row, col) :: res)
              case _ => adjacents(b, c, p, tail, res)
            }
          }
      }
    }

    adjacents(board, cell, pos, adjacency, Nil)
  }

}
