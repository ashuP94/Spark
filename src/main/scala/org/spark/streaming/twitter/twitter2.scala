package org.spark.streaming.twitter

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import twitter4j.conf.ConfigurationBuilder
import twitter4j.auth.OAuthAuthorization
import twitter4j.Status
import org.apache.spark.streaming.twitter.TwitterUtils
import org.spark.streaming.twitter.kafkaStreaming.{outputTopic, processedStream, properties}
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.LongSerializer
import org.scalatest.path


object twitter2  extends App{

    if (args.length < 4) {
      System.err.println("Usage: TwitterData <ConsumerKey><ConsumerSecret><accessToken><accessTokenSecret>" +
        "[<filters>]")
      System.exit(1)
    }
    val appName = "TwitterData"
    val conf = new SparkConf()
    conf.setAppName(appName).setMaster("local[2]")


    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.sparkContext.setLogLevel("ERROR")
    val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = args.take(4)
    val filters = args.takeRight(args.length - 4)
    val cb = new ConfigurationBuilder
    cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)
    val auth = new OAuthAuthorization(cb.build)
    val tweets = TwitterUtils.createStream(ssc, Some(auth), filters, StorageLevel.MEMORY_AND_DISK_2).
      //map(x=> Option(x.getUser.getLocation).toString+" "+x.getSource+" "+x.getUser.getTimeZone+" "+x.getUser.isVerified.toString)
  map(x=> Option(x.getSource).toString).cache()
  val iphone_tweets = tweets.map(x=>x.split(">")).map(x=>x(1)).filter(name => !name.contains("iPhone") && !name.contains("Android"))
  val android_tweets = tweets.map(x=>x.split(">")).map(x=>x(1)).filter(_.contains("Android"))
  tweets.print()
  iphone_tweets.print()
  //android_tweets.print()

    val broker = "localhost:9092"
    val properties = new Properties()
    properties.put("bootstrap.servers", broker)
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    /*properties.put("serializer.class", "kafka.serializer.StringEncoder")*/

    val outputTopic="test"

    val producer = new KafkaProducer[String, String](properties)
  tweets.foreachRDD(rdd =>

      rdd.foreach {
        case data: String => {
          val message = new ProducerRecord[String, String](outputTopic, data)
          producer.send(message).get().toString
        }
      }

    )


   // tweets .saveAsTextFiles("tweets", "json")
    ssc.start()
    ssc.awaitTermination()

}