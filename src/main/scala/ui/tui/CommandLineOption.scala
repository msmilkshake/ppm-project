package ui.tui

import logic.GameState

case class CommandLineOption(name: String, exec: GameContainer => GameContainer)
