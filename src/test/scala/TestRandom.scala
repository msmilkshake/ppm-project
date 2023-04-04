import logic.MyRandom

object TestRandom {
  def main(args: Array[String]): Unit = {
    val rand = MyRandom(0x54321)
    val (n1, newRand1) = rand.nextInt(2)
    val (n2, newRand2) = newRand1.nextInt(2)
    val (n3, newRand3) = newRand2.nextInt(2)
    val (n4, newRand4) = newRand3.nextInt(2)
    val (n5, newRand5) = newRand4.nextInt(2)
    
    println(f"${n1 + 1}\n${n2 + 1}\n${n3 + 1}\n${n4 + 1}\n${n5 + 1}")
  }
}
