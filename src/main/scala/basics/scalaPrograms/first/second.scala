package basics.scalaPrograms.first

class second {
protected def meth(s:String):String = {
s.toLowerCase
}

}

object second{
  def main(args: Array[String]): Unit = {
    println("Enter the number of digits")
    val lastValue = scala.io.StdIn.readInt()
    val pie = math.Pi
    val pie1 = 22.0/7
    println(pie.toString)
  }
  val objmeth = new second
  println(objmeth.meth("aocucnnco'cv"))
}