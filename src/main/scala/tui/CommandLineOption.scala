package tui

case class CommandLineOption(name: String, exec: Container => Container)
