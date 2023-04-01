import scala.annotation.tailrec

object Hex extends App {
    val r = new MyRandom(73)

  def randomMove(rand: MyRandom): ((Int, Int), MyRandom) = {
    ((rand.nextInt(5), rand.nextInt(5)), rand)
  }

  //  val s = GameState(0)
  //  mainLoop(s, r)
  //  @tailrec
  //  def mainLoop(gameState: GameState, random: MyRandom): Unit = {
  // a) prompt the user for input
  GameUtils.showPrompt()
  println(randomMove(r))
  // b) get the user's input
  // c) make the move
  // d) check for win condition
  // e) make the computer move
  // f) check for win condition
  // g) game not finished, loop again
  //    mainLoop(newGameState, random)
  //  }
}
