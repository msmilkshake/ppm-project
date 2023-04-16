package core

import core.Board.Board
import core.Cells.Cell
import core.Difficulty.Difficulty

case class GameState(boardLen: Int,
                     board: Board,
                     computerDifficulty: Difficulty,
                     random: MyRandom,
                     winner: Cell)
