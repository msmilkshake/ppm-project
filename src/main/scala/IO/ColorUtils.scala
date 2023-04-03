package IO

object ColorUtils {
  val blue = "\u001b[0;34m"
  val red = "\u001b[0;31m"
  val std = "\u001b[0m"

  def blueColor(s: String): String = {
    f"${blue}${s}${std}"
  }

  def redColor(s: String): String = {
    f"${red}${s}${std}"
  }
}

