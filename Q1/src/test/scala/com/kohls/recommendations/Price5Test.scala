package com.kohls.recommendations

import scala.collection.mutable
import com.kohls.recommendations._
import com.twitter.scalding._
import org.scalatest._
import com.kohls.recommendations.impl.ReccommendProductPrices

//import org.junit.runner.RunWith
//import org.scalatest.junit.JUnitRunner
//@RunWith(classOf[JUnitRunner])

class Price5Test extends WordSpec with Matchers {
  /* Import Dsl._ to get implicit conversions from List[Symbol] -> cascading.tuple.Fields etc */
  import Dsl._

  val ProductsSchema = List('productId, 'brand, 'style, 'gender, 'primaryType_t1, 'type_t2, 'subType_t3, 'color)
  val CatRecoSchema = List('full_cat, 'RecommendedProductIds)

  val PriceSchema = List('productIdPrc, 'maxSalePrice, 'minSalePrice)
  val t2 = List('full_cat, 'RecommendedProductIds)

  val (prdtData, catsData, prcData) = RecommendTestsDataInit.getObjs1()
  val (recoDataExp) = RecommendTestsDataInit.getExpected1();
  val errData = "";
  val dbgData = "";
  val finalData = ""

  "The Top 5 Recommendations by Price job v5" should {
    JobTest("com.kohls.recommendations.impl.ReccommendProductPrices")
      .arg("input", "input")
      .arg("inRCats", "inRCats")
      .arg("inPrice", "inPrice")
      .arg("debug", "false")
      //.arg("--debug", "false")
      .arg("output", "output")
      .source(Tsv("input", ProductsSchema), prdtData)
      .source(Tsv("inRCats", CatRecoSchema), catsData)
      .source(Tsv("inPrice", PriceSchema), prcData)

      .sink[String](Tsv(RPaths.prodAfterCatJoin)) {
        outputBuffer =>
          val result = outputBuffer
          "prodAfterCatJoin" in {
            println("prodAfterCatJoin ")

          }
      }

      .sink[String](Tsv(RPaths.joinProdRecoCat)) {
        outputBuffer =>
          val result = outputBuffer
          "joinProdRecoCat" in {
            println("joinProdRecoCat ")

          }
      }

      .sink[String](Tsv(RPaths.ProdAfterJoin)) {
        outputBuffer =>
          val result = outputBuffer
          "pricesRead2_err" in {
            println("pricesRead2_err ")

          }
      }
      .sink[String](Tsv(RPaths.ProdWithRecoErrs)) {
        outputBuffer =>
          val result = outputBuffer
          "pjoin_pro-pric-rec_err6" in {
            println("join_pro-pric-rec_err ")
          }
      }

      .sink[String](Tsv(RPaths.ProdCatErrs)) {
        outputBuffer =>
          val result = outputBuffer
          "ProdCatErrs" in {
            println("ProdCatErrs  " + result)
          }
      }

      .sink[String](Tsv(RPaths.PricePipeErrs)) {
        outputBuffer =>
          val result = outputBuffer
          "PricePipeErrs" in {
            println("PricePipeErrs " + result)
            //result shouldEqual recoDataExp
          }
      }

      .sink[String](Tsv(RPaths.JoinPricePrdErr)) {
        outputBuffer =>
          val result = outputBuffer
          "JoinPricePrdErr" in {
            println("JoinPricePrdErr " + result)
          }
      }

      //: mutable.Buffer[(String, String)]
      .sink[(String)](Tsv("output")) {
        outputBuffer =>
          val result = outputBuffer
          "must merge products with prices & recommendations" in {
            println("errData  " + errData + "\n dbgData " + dbgData + "\n")
            println("result1 " + result)
            result shouldEqual recoDataExp
          }
      }.run
      .finish

  }
}