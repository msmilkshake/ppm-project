package Core

import Core.Board.Board
import Core.Cells.Cell

case class GameState(board: Board, rnd: MyRandom, winner: Cell )
