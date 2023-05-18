package io

import core.Board.Board
import core.Cells.{Blue, Cell, Empty, Red}
import core.Coord.Coord
import core.Difficulty.{Easy, Medium}
import core.ProgramState.{InMainMenu, ProgramState}
import core.{GameState, MyRandom}
import tui.Container

import java.io.File
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.annotation.tailrec
import scala.language.postfixOps

object Serializer {

  // Just a BIG prime number to scramble the string saved as binary
  val saveEncoding = 0xCF17F32C9F7L
  

  def serializeContainer(c: Container, file: String): Unit = {
    new File(f"${IOUtils.saveFolderPath}/last").mkdirs()
    val channel: FileChannel =
      FileChannel.open(Paths.get(f"${IOUtils.saveFolderPath}$file.sav"),
      StandardOpenOption.CREATE, StandardOpenOption.WRITE)
    channel.write(stateToByteBuffer(c.gameState))
    c.playHistory map (state => channel.write(stateToByteBuffer(state.asInstanceOf[GameState])))//TODO
    channel.close()
  }

  def saveGameAuto(c: Container): Unit = {
    serializeContainer(c, IOUtils.continuePath)
  }
  
  def saveGame(c: Container): Unit = {
    val saveName = IOUtils.promtSaveFilename()
    serializeContainer(c, saveName)
    IOUtils.displaySaveSuccess(saveName)
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
    val header = f"${
      gs.difficulty match {
        case core.Difficulty.Easy => "E"
        case core.Difficulty.Medium => "M"
      }
    },${gs.random.getSeed()}"

    f"$header\n${boardToStr(gs.board.get)}\n"
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
  
  def getLastSavedGame(c: Container): Container = {
    getSavedGame(f"${IOUtils.saveFolderPath}${IOUtils.continuePath}", c, InMainMenu)
  }

  def getSavedGame(path: String, c: Container, ps: ProgramState): Container = {

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

    Container(gs, history.asInstanceOf[List[Coord]], ps, c.newGameSettings) //TODO
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
    val difficulty = headerSplit(0) match {
      case "E" => Easy
      case "M" => Medium
    }
    val rand = MyRandom(headerSplit(1).toLong)

    val board: Board = (boardLines foldRight List[List[Cell]]())((line, board) => {
      (line.split("") foldRight List[Cell]())((cellStr, list) => {
        cellStr match {
          case "-" => Empty :: list
          case "B" => Blue :: list
          case "R" => Red :: list
        }
      }) :: board
    })

    GameState(Some(board), difficulty, rand, None)
  }

}
