import scala.annotation.tailrec

object Hex extends App {
   // type Board = List[List[Cells.Cell]]
    val r = new MyRandom(73) // seed
    val row = 5 // row coordinate
    val col = 5 // column coordinate

  def randomMove(board: Board, rand: MyRandom): ((Int, Int), MyRandom) = {
    ((rand.nextInt(row), rand.nextInt(col)), rand)
  }

  //  val s = GameState(0)
  //  mainLoop(s, r)
  //  @tailrec
  //  def mainLoop(gameState: GameState, random: MyRandom): Unit = {
  // a) prompt the user for input
  GameUtils.showPrompt()
  println()
  println(randomMove(new Board(3, 5), r))
  // b) get the user's input
  // c) make the move
  // d) check for win condition
  // e) make the computer move
  // f) check for win condition
  // g) game not finished, loop again
  //    mainLoop(newGameState, random)
  //  }
}
