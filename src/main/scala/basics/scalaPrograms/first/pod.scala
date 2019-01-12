package basics.scalaPrograms.first

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object pod {
  def main(args: Array[String]): Unit = {
    println("hello")
    val conf = new SparkConf()
    conf.setAppName("podCount").setMaster("local[2]")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val file = sc.textFile("""C:\Users\A638015\Documents\pods.txt""")
    val rFile = file.map(_.split(" ")).map(w => (w(10),1)).reduceByKey(_+_)
    val mFile = rFile.filter(_._2 > 1).sortBy(_._2,ascending = false).collect()
  }

}
