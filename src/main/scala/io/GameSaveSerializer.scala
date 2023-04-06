package io

import logic.Board.Board
import logic.Cells.{Blue, Cell, Empty, Red}
import logic.Difficulty.{Easy, Medium}
import logic.ProgramState.GameRunning
import logic.{GameState, MyRandom}
import tui.Container

import java.io.{File, PrintWriter}
import scala.annotation.tailrec
import scala.io.Source
import scala.language.postfixOps

object GameSaveSerializer {

  def serializeContainer(c: Container): Unit = {
    val writer = new PrintWriter(new File(IOUtils.saveFilePath))
    writer.write(stateToStr(c.gameState))
    c.stateHistory map (state => writer.write(stateToStr(state)))
    writer.close()
  }

  def stateToStr(gs: GameState): String = {
    val header = f"${gs.boardLen},${
      gs.computerDifficulty match {
        case logic.Difficulty.Easy => "E"
        case logic.Difficulty.Medium => "M"
      }
    },${gs.random.seed}"

    f"$header\n${boardToStr(gs.board)}\n"
  }
  
  def boardToStr(board: Board): String = {
    (board foldLeft "")((acc, line) => {
      val strLine = (line foldLeft acc)((result, cell) => {
        cell match {
          case logic.Cells.Red => f"${result}R"
          case logic.Cells.Blue => f"${result}B"
          case logic.Cells.Empty => f"${result}-"
        }
      })
      f"$strLine\n"
    })
  }

  def getSavedGame(): Container = {
    
    @tailrec
    def buildMoveHistory(linesList: List[String], history: List[GameState]): List[GameState] = {
      linesList match {
        case Nil => history
        case other =>
          val (remainingLines, state) = buildGameState(other)
          buildMoveHistory(remainingLines, history :+ state)
      }
    }
    
    def buildGameState(linesList: List[String]): (List[String], GameState) = {
      val header = linesList.head
      val (remainingLines, boardLines) = getBoardStrings(linesList.tail, Nil)
      (remainingLines, deserializeGameState(header, boardLines))
    }
    
    val src = Source.fromFile(IOUtils.saveFilePath)
    val linesList = src.getLines().toList
    src.close()
    
    val (remainingLines, gs) = buildGameState(linesList)
    val history = buildMoveHistory(remainingLines, Nil)
    
    Container(gs, history, GameRunning, IOUtils.checkSaveExists())
  }

  @tailrec
  def getBoardStrings(content: List[String], lst: List[String]): (List[String], List[String]) = {
    content match {
      case separator :: tail if separator isBlank => (tail, lst)
      case line :: tail => getBoardStrings(tail, lst :+ line)
    }
  }
  
  def deserializeGameState(header: String, boardLines: List[String]): GameState = {
    val headerSplit = header.split(",")
    val boardLen = headerSplit(0).toInt
    val difficulty = headerSplit(1) match {
      case "E" => Easy
      case "M" => Medium
    }
    val rand = MyRandom(headerSplit(2).toLong)

    val board: Board = (boardLines foldRight List[List[Cell]]())((line, board) => {
      (line.split("") foldRight List[Cell]())((cellStr, list) => {
        cellStr match {
          case "-" => Empty :: list
          case "B" => Blue :: list
          case "R" => Red :: list
        }
      }) :: board
    })

    GameState(boardLen, board, difficulty, rand, Empty)
  }

}
