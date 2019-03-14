package org.spark.streaming.twitter

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._

object kafkaStreaming extends App {

  val conf = new SparkConf().setMaster("local[*]").setAppName("SimpleDStreamExample")
  val inputTopic = "javainuse-topic"
  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "spark-demo",
    "kafka.consumer.id" -> "kafka-consumer-01"
  )
  val ssc = new StreamingContext(conf, Seconds(10))
  ssc.sparkContext.setLogLevel("ERROR")

  val inputStream = KafkaUtils.createDirectStream(ssc,
    PreferConsistent, Subscribe[String, String](Array(inputTopic), kafkaParams))
  val processedStream = inputStream.map(record => record.value.toUpperCase) //Any operation can be performed here.

  //checking the batches for data
  processedStream.print()

  val broker = "localhost:9092"
  val properties = new Properties()
  properties.put("bootstrap.servers", broker)
  properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val outputTopic="test"

  val producer = new KafkaProducer[String, String](properties)
  processedStream.foreachRDD(rdd =>

    rdd.foreach {
      case data: String => {
        val message = new ProducerRecord[String, String](outputTopic, data)
        producer.send(message).get().toString
      }
    }

  )

  ssc.start()
  ssc.awaitTermination()

}
