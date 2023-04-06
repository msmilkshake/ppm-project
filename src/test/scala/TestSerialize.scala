import Tests._
import io.GameSaveSerializer

object TestSerialize extends App {
  println(GameSaveSerializer.boardToStr(b1))
  println(GameSaveSerializer.boardToStr(b2))
  println(GameSaveSerializer.boardToStr(b3))
  println(GameSaveSerializer.boardToStr(b4))
  println(GameSaveSerializer.boardToStr(b5))
  println(GameSaveSerializer.boardToStr(a1))
  println(GameSaveSerializer.boardToStr(a2))
  println(GameSaveSerializer.boardToStr(a3))
}
