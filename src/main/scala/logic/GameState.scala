package logic

import logic.Board.Board
import logic.Difficulty.Difficulty

case class GameState(boardLen: Int,
                     board: Board,
                     computerDifficulty: Difficulty,
                     random: MyRandom) {
}

object GameState {
  
}
