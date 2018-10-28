package org.spark.streaming.twitter

import org.apache.spark.SparkConf
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object twitter {
    def main(args: Array[String])
  {
    println("hello")

    if (args.length < 4){
      System.err.println("Usage: twitterstream <consumer key> <consumer secret> " + "<access token> <access token secret> [<filters>]")
      System.exit(1)

    }
    //  StreamingExamples.setStreamingLogLevels()

    val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = args.take(4)
    val filters = args.takeRight(args.length - 4)

    println(consumerKey)
    println(consumerSecret)
    println(accessToken)
    println(accessTokenSecret)

    System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)

    val sparkConf = new SparkConf().setAppName("TwitterPopularTags").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(1))
    val stream = TwitterUtils.createStream(ssc, None, filters)

    val hashTags = stream.map(status => status.getText)

    val topCounts60 = hashTags.map(word => (word , 1)).reduceByKeyAndWindow(_ + _, Seconds(120)).map{case (topic, count) => (count, topic)}.transform(_.sortByKey(false))
    val topCounts10 = hashTags.map(word =>( word , 1)).reduceByKeyAndWindow(_ + _, Seconds(10)).map{case (topic, count) => (count, topic)}.transform(_.sortByKey(false))

    topCounts60.foreachRDD(rdd => {
      val topList = rdd.take(120)
      println("\nPopular topics in last 120 seconds (%s total):".format(rdd.count()))
      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
    })

    topCounts10.foreachRDD(rdd => {
      val topList = rdd.take(10)
      println("\nPopular topics in last 10 seconds (%s total):".format(rdd.count()))
      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
    })

    ssc.start()
    ssc.awaitTermination()


  }
}