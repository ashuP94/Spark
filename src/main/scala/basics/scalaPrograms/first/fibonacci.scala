package basics.scalaPrograms.first

import scala.io.StdIn.readInt

object fibonacci {

  def main(args: Array[String]): Unit = {

   /* println("enter number")
    val num = readInt()
    for(  i <- 1 to num){
       val sum = i + sum
      println(sum)
    }
*/
   val fibs = Stream.iterate( (0,1) ) { case (a,b)=>(b,a+b)  }.map(_._1)
    println(fibs take 10 toList)

    println(fibSeq(30))

   // val fibs1: Stream[Int] = 0 #:: fibs1.scanLeft(1)(_ + _)
  }

  def fibSeq(n: Int): List[Int] = {
    var ret = scala.collection.mutable.ListBuffer[Int](1, 2)
    while (ret(ret.length - 1) < n) {
      val temp = ret(ret.length - 1) + ret(ret.length - 2)
      if (temp >= n) {
        return ret.toList
      }
      ret += temp
    }
    ret.toList
  }
  def fib1( n : Int) : Int = n match {
    case 0 | 1 => n
    case _ => fib1( n-1 ) + fib1( n-2 )
  }

}
