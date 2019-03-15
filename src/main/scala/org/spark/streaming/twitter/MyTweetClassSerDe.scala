/*
package org.spark.streaming.twitter

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import java.util
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.common.serialization.Deserializer
import java.io.IOException
import org.apache.log4j.Logger


import com.fasterxml.jackson.databind.DeserializationFeature
import java.io.IOException

class MyTweetClassSerDe extends Serializer with Deserializer {
  final private val logger = Logger.getLogger(classOf[MyTweetClassSerDe])

  def configure(map: Nothing, bln: Boolean): Unit = {
  }

  def serialize(string: String, t: Any): Array[Byte] = {
    val objectMapper = new ObjectMapper
    var json_bytes = null
    try
      json_bytes = objectMapper.writeValueAsString(t.asInstanceOf[Nothing])
    catch {
      case ex: Nothing =>
        logger.error("Error in MyTweetClassSerDe.serialize.." + ex.getMessage)
    }
    if (json_bytes != null) json_bytes.getBytes
    else null
  }

  def close(): Unit = {
  }

  def deserialize(string: String, bytes: Array[Byte]): Any = {
    val objectMapper = new Nothing
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    var tweet_obj = null
    try
      tweet_obj = objectMapper.readValue(bytes, classOf[Nothing]).asInstanceOf[Nothing]
    catch {
      case ex: IOException =>
        logger.error("Error in MyTweetClassSerDe.deSerialize.." + ex.getMessage)
    }
    tweet_obj
  }
}

*/
