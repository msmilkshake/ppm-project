package logic

import logic.Cells.{Board, Cell}
import logic.PlayerType.PlayerType

case class GameState(boardLen: Int,
                     board: Board,
                     players: (PlayerType, PlayerType),
                     random: MyRandom,
                     firstPlayer: Int,
                     turn: Int,
                     isRandom: Boolean,
                     saveExists: Boolean) {
}

object GameState {
  
}
