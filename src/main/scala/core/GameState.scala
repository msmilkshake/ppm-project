package core

import core.Board.Board
import core.Cells.Cell
import core.Difficulty.Difficulty

case class GameState(board: Option[Board],
                     difficulty: Difficulty,
                     random: RandomWithState,
                     winner: Option[Cell])
