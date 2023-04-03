package logic

import logic.Cells.{Board, Cell}
import logic.PlayerType.PlayerType

case class GameState(boardLen: Int,
                     board: Board,
                     players: (PlayerType, PlayerType),
                     random: MyRandom,
                     firstPlayer: Int,
                     saveExists: Boolean,
                     movesHistory: List[Board]) {
  

  def setFirstPlayer(firstPlayer: Int, gameState: GameState) =
    GameState.setFirstPlayer(firstPlayer, gameState)

  def changeBoardLen(boardLen: Int, gameState: GameState): GameState =
    GameState.changeBoardLen(boardLen, gameState)

  def changePlayers(players: (PlayerType, PlayerType), gameState: GameState): GameState =
    GameState.changePlayers(players, gameState)
  
  def playTurn() = {

  }

}

object GameState {

  def setFirstPlayer(firstPlayer: Int, gs: GameState) = {
    GameState(gs.boardLen, gs.board, gs.players, gs.random,
      firstPlayer, gs.saveExists, gs.movesHistory)
  }

  def changeBoardLen(boardLen: Int, gs: GameState): GameState = {
    GameState(boardLen, gs.board, gs.players, gs.random,
      gs.firstPlayer, gs.saveExists, gs.movesHistory)
  }

  def changePlayers(players: (PlayerType, PlayerType), gs: GameState): GameState = {
    GameState(gs.boardLen, gs.board, players, gs.random,
      gs.firstPlayer, gs.saveExists, gs.movesHistory)
  }

  def play(board: Board, player: Cell, row: Int, col: Int): Board = {
    ???
  }
}
