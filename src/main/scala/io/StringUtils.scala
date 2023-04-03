package io

object StringUtils {
  val blueColor = "\u001b[0;34m"
  val redColor = "\u001b[0;31m"
  val resetColor = "\u001b[0m"

  def blueString(s: String): String = {
    f"${blueColor}${s}${resetColor}"
  }

  def redString(s: String): String = {
    f"${redColor}${s}${resetColor}"
  }
}
