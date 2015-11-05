package com.kohls.recommendations.impl

import com.kohls.recommendations.ProductCommon
import com.kohls.recommendations.ProductCommon.debug
import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe
import com.kohls.recommendations.PipeCommon
import com.kohls.recommendations.RPaths
import cascading.pipe.joiner.InnerJoin
/**
 * @author Tushar
 */

/**
 * Test, partly random data.
 * WIP
 */
class MakeTestData(args: Args) extends App {
  val prods = scala.collection.mutable.Map[String, Product]()
  val rnd = new java.util.Random
  var i = rnd.nextInt(52)

  def wordPad(pre: String, ln: Int): String = {

    var i = rnd.nextInt(52)
    var j = 0
    val sb = new StringBuilder
    sb.append(pre)
    for (j <- 1 to ln) {
      if (i < 26) {
        sb.append((65 + i).toChar)
      } else {
        sb.append((97 + i).toChar)
      }
      i = rnd.nextInt(52)
    }
    sb.toString
  }
}