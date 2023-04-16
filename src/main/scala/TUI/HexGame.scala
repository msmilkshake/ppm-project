package TUI

import Core.Cells.{Blue, Empty, Red}
import Core.GameCore.playTurn
import Core.{Board, GameState, MyRandom, ProgramState}
import IO.IOUtils.optionPrompt
import IO.{BoardPrinter, IOUtils}

object HexGame extends App {

  val c = Container(
    GameState(Board.firstBoard(5), MyRandom(0x4321), Empty),
    Nil,
    ProgramState.Menu,
    5
  )


  mainLoop(c)

  def mainLoop(c: Container): Unit = {
    c.ps match {
      case ProgramState.Menu =>
        optionPrompt("Main Menu", Menu.mainMenu) match {
          case None =>
            // Mensagem Opção Inválida
            mainLoop(c)
          case Some(option) =>
            mainLoop(option.exec(c))
        }
      case ProgramState.Settings => optionPrompt("Settings", Menu.settingsMenu) match {
        case None =>
          // Mensagem Opção Inválida
          mainLoop(c)
        case Some(option) =>
          mainLoop(option.exec(c))
      }
      case ProgramState.Running => {
        mainLoop(playTurn(c))

      }
      case Core.ProgramState.Exit => //mensagem despedida
    }

  }

}
