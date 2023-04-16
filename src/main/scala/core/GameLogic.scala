package core

import io.{SaveState, IOUtils}
import core.Board.Board
import core.Cells.{Blue, Cell, Empty, Red}
import core.Coord.Coord
import core.Difficulty.Difficulty
import core.ProgramState.{GameWon, MainMenu, ProgramState, Undo}
import tui.Container

import scala.annotation.tailrec

object GameLogic {

  val adjacency: List[Coord] = List(
    (-1, 0), // top-left
    (-1, 1), // top-right
    (0, 1), // right
    (1, 0), // bottom-right
    (1, -1), // bottom-left
    (0, -1), // left
  )

  @tailrec
  def playerMove(gs: GameState): (GameState, ProgramState) = {
    IOUtils.actionPrompt(Red, gs.board) match {
      case (None, state) => (gs, state)
      case (Some((row, col)), state) =>
        gs.board(row - 1)(col - 1) match {
          case Empty =>
            val newBoard = play(gs.board, Red, row - 1, col - 1)
            (GameState(gs.boardLen,
              newBoard,
              gs.computerDifficulty,
              gs.random,
              gs.winner), state)
          case _ =>
            IOUtils.warningOccupiedCell()
            playerMove(gs)
        }
    }
  }

  def computerMove(gs: GameState, computer: Difficulty): GameState = {
    computer match {
      case core.Difficulty.Easy => easyMove(gs)
      case core.Difficulty.Medium => mediumMove(gs)
    }
  }

  def easyMove(gs: GameState): GameState = {
    val ((row, col), newRand) = randomMove(gs.board, gs.random)
    IOUtils.displayComputerPlay(row + 1, col + 1)
    GameState(gs.boardLen,
      play(gs.board, Blue, row, col),
      gs.computerDifficulty,
      newRand,
      gs.winner)
  }

  def mediumMove(gs: GameState): GameState = {
    getAllCells(gs.board, Blue) match {
      case Nil => easyMove(gs)
      case list =>
        val validPositions = list filter (coord => getAdjacents(gs.board, Empty, coord).nonEmpty)
        validPositions match {
          case Nil => easyMove(gs)
          case positions =>
            val (index, newRand) = gs.random.nextInt(validPositions.length)
            val candidates = getAdjacents(gs.board, Empty, positions(index))
            val (playIndex, newRand2) = newRand.nextInt(candidates.length)
            val (row, col) = candidates(playIndex)
            IOUtils.displayComputerPlay(row + 1, col + 1)

            GameState(gs.boardLen,
              play(gs.board, Blue, row, col),
              gs.computerDifficulty,
              newRand2.asInstanceOf[MyRandom],
              gs.winner)
        }
    }
  }

  def play(board: Board, player: Cell, row: Int, col: Int): Board = {
    board.zipWithIndex map {
      case (cells, i) => cells.zipWithIndex map {
        case (cell, j) => if (i == row && j == col) player else cell
      }
    }
  }

  def playTurn(c: Container): Container = {
    val (gs1, state) = playerMove(c.gameState)
    state match {
      case MainMenu =>
        SaveState.serializeContainer(c)
        Container(c.gameState, c.stateHistory, MainMenu, IOUtils.checkSaveExists())
      case Undo =>
        Container(gs1, c.stateHistory, state, c.continueExists)
      case _ =>
        if (hasContiguousLine(gs1.board, Red)) {
          return Container(
            GameState(gs1.boardLen,
              gs1.board,
              gs1.computerDifficulty,
              gs1.random,
              Red),
            c.stateHistory,
            GameWon,
            c.continueExists
          )
        }
        val gs2 = computerMove(gs1, c.gameState.computerDifficulty)
        if (hasContiguousLine(gs2.board, Blue)) {
          return Container(
            GameState(gs2.boardLen,
              gs2.board,
              gs2.computerDifficulty,
              gs2.random,
              Blue),
            c.stateHistory,
            GameWon,
            c.continueExists
          )
        }
        Container(gs2,
          c.gameState :: c.stateHistory,
          c.programState,
          c.continueExists)
    }

  }

  def randomMove(board: Board, rand: MyRandom): ((Int, Int), MyRandom) = {
    val emptyCells = getAllCells(board, Empty)
    val (target, newRand) = rand.nextInt(emptyCells.length)
    (emptyCells(target), newRand.asInstanceOf[MyRandom])
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
    def buildStartLine(b: Board, c: Cell, i: Int, res: List[Coord]): List[Coord] = {
      i match {
        case 0 => res
        case _ =>
          c match {
            case Red => buildStartLine(b, c, i - 1, (i - 1, 0) :: res)
            case Blue => buildStartLine(b, c, i - 1, (0, i - 1) :: res)
          }
      }
    }

    @tailrec
    def winnerPath(b: Board, c: Cell, path: List[Coord]): Boolean = {
      path match {
        case Nil => false
        case (_, col) :: _ if col == b.length - 1 && c == Red => true
        case (row, _) :: _ if row == b.length - 1 && c == Blue => true
        case _ :: tail => winnerPath(b, c, tail)
      }
    }

    @tailrec
    def checkAjacentCells(b: Board, c: Cell, p: Coord, s: Set[Coord],
                          adj: List[Coord], res: List[Coord]): (List[Coord], Set[Coord]) = {
      adj match {
        case Nil => (res, s)
        case (rowOffset, colOffset) :: tail =>
          val (row, col) = (p._1 + rowOffset, p._2 + colOffset)
          if (row < 0 || row >= b.length || col < 0 || col >= b.length) {
            checkAjacentCells(b, c, p, s, tail, res)
          } else {
            b(row)(col) match {
              case cell if cell == c && !s.contains((row, col)) =>
                checkAjacentCells(b, cell, p, s + ((row, col)), tail, (row, col) :: res)
              case _ => checkAjacentCells(b, c, p, s, tail, res)
            }
          }
      }
    }

    @tailrec
    def buildAdjacencyList(b: Board, c: Cell, toCheck: List[Coord],
                           set: Set[Coord]): List[Coord] = {
      toCheck match {
        case Nil => set.toList
        case pos :: posTail if b(pos._1)(pos._2) == c =>
          val (adj, newSet) = checkAjacentCells(b, c, pos, set + pos, adjacency, Nil)
          buildAdjacencyList(b, c, posTail ++ adj, newSet)
        case _ :: posTail => buildAdjacencyList(b, c, posTail, set)
      }
    }

    (buildStartLine(b, c, b.length, Nil) foldRight false)(
      (coord, result) => result ||
        winnerPath(b, c, buildAdjacencyList(b, c, List(coord), Set())))
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
