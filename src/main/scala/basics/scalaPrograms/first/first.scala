package basics.scalaPrograms.first

import scala.io.StdIn._
import scala.io.Source

object first {

  def main(args: Array[String]): Unit = {
    println("Hello World")

    val num = 1 to 20
   /* println("Enter number")
    val a = readInt()
    println("number is " +a )
    num.foreach(println(_))*/
    val st = for(x <- num if x != 3; if x < 8 )yield x
    st.foreach(println(_))

 /* val input = Source.fromFile("""C:\Users\A638015\Desktop\backuppatch.txt""")
 val sm = input.getLines()
    sm.foreach(a => println(a))*/

  }

}
