import Tests._
import io.SaveState

object TestSerialize extends App {
  println(SaveState.boardToStr(b1))
  println(SaveState.boardToStr(b2))
  println(SaveState.boardToStr(b3))
  println(SaveState.boardToStr(b4))
  println(SaveState.boardToStr(b5))
  println(SaveState.boardToStr(a1))
  println(SaveState.boardToStr(a2))
  println(SaveState.boardToStr(a3))
}
