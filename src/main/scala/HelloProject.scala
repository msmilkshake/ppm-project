
object HelloProject {

  def main(args: Array[String]): Unit = {

    val r = MyRandom(10)
    println(r.nextInteger)
    println(r.nextInteger)
    println(r.nextInteger)
  }

}
