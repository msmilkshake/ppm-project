package logic

import logic.Cells.{Board, Cell}
import logic.Player.Difficulty

case class GameState(boardLen: Int,
                     board: Board,
                     difficulty: Difficulty,
                     random: MyRandom,
                     playerPlaysFirst: Boolean) {

  def setPlayerPlaysFirst(playerPlaysFirst: Boolean, gameState: GameState) =
    GameState.setPlayerPlaysFirst(playerPlaysFirst, gameState)

  def changeBoardLen(boardLen: Int, gameState: GameState): GameState =
    GameState.changeBoardLen(boardLen, gameState)

  def changeDifficulty(difficulty: Difficulty, gameState: GameState): GameState =
    GameState.changeDifficulty(difficulty, gameState)


  def playTurn() = {

  }

}

object GameState {

  def setPlayerPlaysFirst(playerPlaysFirst: Boolean, gs: GameState) = {
    GameState(gs.boardLen, gs.board, gs.difficulty, gs.random, playerPlaysFirst)
  }

  def changeBoardLen(boardLen: Int, gs: GameState): GameState = {
    GameState(boardLen, gs.board, gs.difficulty, gs.random, gs.playerPlaysFirst)
  }

  def changeDifficulty(difficulty: Difficulty, gs: GameState): GameState = {
    GameState(gs.boardLen, gs.board, difficulty, gs.random, gs.playerPlaysFirst)
  }

  def play(board: Board, player: Cell, row: Int, col: Int): Board = {
    ???
  }
}
