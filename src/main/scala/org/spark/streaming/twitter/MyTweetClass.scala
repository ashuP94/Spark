/*
package org.spark.streaming.twitter

import java.io.Serializable
import java.util

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
  *
  * @author kamal
  *         Wraps around HashMap to set and get tweet.  Serializer and Deserializer use this
  *         class to receive and send bytes.
  */
class MyTweetClass() extends Serializable {
  this.tweet = new HashMap[_, _]
  private[twittersentiment] var tweet = null

  override def toString: String = "MyTweetClass{" + "tweet=" + tweet + '}'

  def getTweet: util.HashMap[_, _] = tweet

  def setTweet(tweet: util.HashMap[_, _]): Unit = {
    this.tweet = tweet
  }
}
*/
