package core

import core.Board.Board
import core.Cells.Cell
import core.Difficulty.Difficulty

case class GameState(board: Option[Board],
                     difficulty: Difficulty,
                     random: MyRandom,
                     winner: Option[Cell])
