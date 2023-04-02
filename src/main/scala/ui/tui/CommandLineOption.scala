package ui.tui

case class CommandLineOption(name: String, exec: Action => Action)
