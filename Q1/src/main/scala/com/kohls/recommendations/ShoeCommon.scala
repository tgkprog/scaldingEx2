package com.kohls.recommendations

import scala.collection.mutable.ArrayBuffer

import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe

object ShoeCommon {
  implicit def str2bool(string: String): Boolean = string != null && string.toUpperCase.equals("TRUE")

  val SEPERATOR = ","
  val ProductsSchema = List('productId, 'brand, 'style, 'gender, 'primaryType, 'type, 'subType, 'color)
  val CatRecoSchema = List('full_cat, 'RecommendedProductIds)

  val PriceSchema = List('productIdPrc, 'maxSalePrice, 'minSalePrice)
  val Prod_Id = 'productId
  val PriceAvg = List('productIdPrc, 'prc)

  val CAT_TYPES_PROD = ('primaryType, 'type, 'subType)
  val CAT_TYPES_RECO = ('rt1, 'rt2, 'rt3)
  val PROD_AND_RECO = ('productId, 'RecommendedProductIds)
  val CAT_TYPES_RECO_IDs = ('rt1, 'rt2, 'rt3, 'RecommendedProductIds)
  val RECO_PROD_IDS = 'RecommendedProductIds
  val RECO_PROD_ID = 'RecommendedProductId
  val PROD_ID_PRC = 'productIdPrc
  val prodIdTypes = ('productId, 'primaryType, 'type, 'subType)
  val gender = ('productId, 'gender)
  var debug = true

  def remove(num: Int, list: Array[String]) = list diff List(num)

  def init(args: Args) = {
    debug = args.getOrElse(("debug"), "true")
    println("Debug : " + debug)
  }

  def strToArrRemoveMatch(a: String, p: String, split: String = SEPERATOR): String = {
    if (a == null || a.length == 0) {
      ""
    } else {
      val ar = a.split(split)
      arrRemoveMatch(ar, p)
    }
  }

  def arrRemoveMatch(ar: Array[String], p: String): String = {
    val b = for (e <- ar if !e.equals(p)) yield e
    b.mkString(SEPERATOR)

  }

  //return a string with seperator. After removing spcified find string and retaining max elements
  def procStrSz(in: String, find: String, max: Int = -1, sepe: String = SEPERATOR): String = {
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
    val b = procArrSz(ar, find, max)
    b.mkString(sepe)
  }

  def procArrSz(ar: Array[String], find: String, max: Int = -1) = {
    var i = 0;
    var work: Boolean = true
    val bu: ArrayBuffer[String] = ArrayBuffer()
    do {
      if (!ar(i).equals(find)) {
        bu.+=(ar(i))
        if (max == bu.length) work = false
      }
      i += 1
      if (i == ar.length) work = false
    } while (work)
    bu
  }
}