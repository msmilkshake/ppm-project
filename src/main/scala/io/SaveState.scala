package io

import core.Board.Board
import core.Cells.{Blue, Cell, Empty, Red}
import core.Difficulty.{Easy, Medium}
import core.ProgramState.GameRunning
import core.{GameState, MyRandom}
import tui.Container

import java.io.{File, PrintWriter}
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.annotation.tailrec
import scala.io.Source
import scala.language.postfixOps

object SaveState {

  // Just a BIG prime number to scramble the string saved as binary
  val saveEncoding = 0xCF17F32C9F7L

  def serializeContainer(c: Container): Unit = {
    serializeContainer(c, IOUtils.continuePath)
  }

  def serializeContainer(c: Container, filePath: String): Unit = {
    new File(IOUtils.saveFolderPath).mkdirs()
    val channel: FileChannel =
      FileChannel.open(Paths.get(f"${IOUtils.saveFolderPath}$filePath.sav"),
      StandardOpenOption.CREATE, StandardOpenOption.WRITE)
    channel.write(stateToByteBuffer(c.gameState))
    c.stateHistory map (state => channel.write(stateToByteBuffer(state)))
    channel.close()
  }

  def stateToByteBuffer(gs: GameState): ByteBuffer = {
    val stateString: String = stateToStr(gs)
    val byteBuffer: ByteBuffer = ByteBuffer.allocate(stateString.length * 8)
    (stateString.chars().asLongStream().map(c => c * saveEncoding).toArray
      foldLeft byteBuffer)((buffer, long) => buffer.putLong(long)).flip()
  }
  
  def bytesToStr(bytes: Array[Byte]): String = {
    val longs: List[Long] = (bytes.grouped(8) map
      (group => ByteBuffer.wrap(group).getLong())).toList
    
    (longs foldLeft "")((a, b) => f"$a${(b / saveEncoding).toChar}")
  }
  

  def stateToStr(gs: GameState): String = {
    val header = f"${gs.boardLen},${
      gs.computerDifficulty match {
        case core.Difficulty.Easy => "E"
        case core.Difficulty.Medium => "M"
      }
    },${gs.random.seed}"

    f"$header\n${boardToStr(gs.board)}\n"
  }

  def boardToStr(board: Board): String = {
    (board foldLeft "")((acc, line) => {
      val strLine = (line foldLeft acc)((result, cell) => {
        cell match {
          case core.Cells.Red => f"${result}R"
          case core.Cells.Blue => f"${result}B"
          case core.Cells.Empty => f"$result-"
        }
      })
      f"$strLine\n"
    })
  }
  
  def getLastSavedGame(): Container = {
    getSavedGame(f"${IOUtils.saveFolderPath}${IOUtils.continuePath}")
  }

  def getSavedGame(path: String): Container = {

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

    val linesList = bytesToStr(Files.readAllBytes(Paths.get(f"$path.sav")))
      .split("\n").toList

    val (remainingLines, gs) = buildGameState(linesList)
    val history = buildMoveHistory(remainingLines, Nil)

    Container(gs, history, GameRunning, IOUtils.checkSaveExists())
  }

  @tailrec
  def getBoardStrings(content: List[String], lst: List[String]): (List[String], List[String]) = {
    content match {
      case Nil => (Nil, lst)
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
