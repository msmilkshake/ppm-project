package logic

import logic.Cells.{Board, Cell}
import logic.PlayerType.Type

case class GameState(boardLen: Int,
                     board: Board,
                     players: (Type, Type),
                     random: MyRandom,
                     firstPlayer: Int,
                     running: Boolean) {
  

  def setFirstPlayer(firstPlayer: Int, gameState: GameState) =
    GameState.setFirstPlayer(firstPlayer, gameState)

  def changeBoardLen(boardLen: Int, gameState: GameState): GameState =
    GameState.changeBoardLen(boardLen, gameState)

  def changePlayers(players: (Type, Type), gameState: GameState): GameState =
    GameState.changePlayers(players, gameState)
  
  def playTurn() = {

  }

}

object GameState extends Enumeration {

  def setFirstPlayer(firstPlayer: Int, gs: GameState) = {
    GameState(gs.boardLen, gs.board, gs.players, gs.random, firstPlayer, gs.running)
  }

  def changeBoardLen(boardLen: Int, gs: GameState): GameState = {
    GameState(boardLen, gs.board, gs.players, gs.random, gs.firstPlayer, gs.running)
  }

  def changePlayers(players: (Type, Type), gs: GameState): GameState = {
    GameState(gs.boardLen, gs.board, players, gs.random, gs.firstPlayer, gs.running)
  }

  def play(board: Board, player: Cell, row: Int, col: Int): Board = {
    ???
  }
}
