package shoes

import scala.collection.mutable.ArrayBuffer

import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe

object ShoeCommon {
  implicit def str2bool(string: String): Boolean = string == null || string.toUpperCase.equals("TRUE")

  val ProductsSchema = List('productId, 'brand, 'style, 'gender, 'primaryType_t1, 'type_t2, 'subType_t3, 'color)
  val CatRecoSchema = List('full_cat, 'RecommendedProductIds)

  val PriceSchema = List('productIdPrc, 'maxSalePrice, 'minSalePrice)
  var debug = true

  def remove(num: Int, list: Array[String]) = list diff List(num)

  def init(args : Args) = {
    debug = args.getOrElse(("debug"), "true")
  }
  
  def strToArrRemoveMatch(a: String, p: String, split: String = ","): String = {
    if (a == null || a.length == 0) {
      ""
    } else {
      val ar = a.split(split)
      arrRemoveMatch(ar, p)
    }
  }

  def arrRemoveMatch(ar: Array[String], p: String): String = {
    val b = for (e <- ar if !e.equals(p)) yield e
    b.mkString(",")

  }

  def procStrSz(in: String, p: String, max: Int = -1, sepe: String = ","): String = {
    if (in == null || in.length == 0) {
      return ""
    }
    var in2: String = null
    if (in.charAt(0) == sepe.charAt(0)) {
      in2 = in.substring(1)
    } else {
      in2 = in
    }
    val ar = in2.split(sepe)
    val b = procArrSz(ar, p, max)
    b.mkString(sepe)
  }

  def procArrSz(ar: Array[String], p: String, max: Int = -1) = {
    var i = 0;
    var work: Boolean = true
    val bu: ArrayBuffer[String] = ArrayBuffer()
    do {
      if (!ar(i).equals(p)) {
        bu.+=(ar(i))
        if (max == bu.length) work = false
      }
      i += 1
      if (i == ar.length) work = false
    } while (work)
    bu
  }
}