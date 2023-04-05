package logic

import logic.Board.Board
import logic.Cells.{Cell, Red, Blue}
import logic.GameLogic.{Coord, adjacency}

import scala.annotation.tailrec

object LogicTest {
  
  def checkWinner(b: Board, c: Cell): Boolean = {

    @tailrec
    def buildStartLine(b: Board, c: Cell, i: Int, res: List[Coord]): List[Coord] = {
      i match {
        case 0 => res
        case n =>
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
  
}
