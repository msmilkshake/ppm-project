package core

case class MyRandom(seed: Long) extends RandomWithState {

  override def nextInt(): (Int, RandomWithState) = MyRandom.nextInt(seed)

  override def nextInt(n: Int): (Int, RandomWithState) = MyRandom.nextInt(n, seed)

  override def getSeed(): Long = seed
}

object MyRandom {
  def nextInt(seed: Long): (Int, RandomWithState) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRandom = MyRandom(newSeed)
    val n = (newSeed >>> 16).toInt
    (n, nextRandom)
  }

  def nextInt(n: Int, seed: Long): (Int, RandomWithState) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRandom = MyRandom(newSeed)
    val nn = (newSeed >>> 16).toInt % n
    (if (nn < 0) -nn else nn, nextRandom)
  }
}
