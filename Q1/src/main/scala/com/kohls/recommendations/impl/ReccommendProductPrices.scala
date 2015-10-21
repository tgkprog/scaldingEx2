package com.kohls.recommendations.impl

import com.kohls.recommendations.ShoeCommon
import com.kohls.recommendations.ShoeCommon.debug
import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe
import com.kohls.recommendations.PipeCommon
import com.kohls.recommendations.RPaths
/**
 * @author Tushar
 */

/**
 * Task 2 : Join products with prices and recommendations
 * Sort by recommended price and retain 5
 */
class ReccommendProductPrices(args: Args) extends PipeCommon(args) {
  val prodsWithReco = processPrice(products, cats)
  writeOutProd(prodsWithReco, args("output"), cats)

  /**
   * Finds average price from prices feed. Joins products with categories.
   * Then with prices, and gets top 5 recommendations by price.
   * Here we are doing the removing self recommendations again :
   * another approach and to keep the two parts different
   * (separate questions, independent tasks that can be run separately).
   */
  def processPrice(products: Pipe, catRecmnds: Pipe) = {
    val joinType = new LeftJoin
    val joinProdWithRecoCat = products.joinWithSmaller((ShoeCommon.CAT_TYPES_PROD -> ShoeCommon.CAT_TYPES_RECO),
      catRecmnds, joinType).addTrap(Tsv(RPaths.ProdWithRecoErrs)).discard(ShoeCommon.CAT_TYPES_PROD)
    val pricePipe = pricePipeGet()
    val joinProdFlatMapPids = joinProdWithRecoCat.flatMap(ShoeCommon.RECO_PROD_IDS -> ShoeCommon.RECO_PROD_ID) {
      txt: String =>
        {
          if (txt == null) {
            Array.empty[String]
          } else {
            txt.split(ShoeCommon.SEPERATOR)
          }
        }
    }
    val prodPrcPids = joinProdFlatMapPids.joinWithSmaller((ShoeCommon.RECO_PROD_ID -> ShoeCommon.PROD_ID_PRC), pricePipe, joinType).addTrap(Tsv(RPaths.JoinPricePrdErr))
    ///if (debug) pr3.write(Tsv("./o1/ProdpriceA.csv"))
    val prodPrcGroped = prodPrcPids.groupBy('productId) { _.sortBy('prc).take(6) }
    ///if (debug) pr4.write(Tsv("./o1/Prodprice-B.csv"))
    //need to refer to productId here so we can filter it out from recommendations during fold.
    val prodFold = prodPrcGroped.groupBy('productId) {
      _.foldLeft((ShoeCommon.RECO_PROD_ID) -> ShoeCommon.RECO_PROD_IDS)("") {
        (s: String, s2: String) =>
          {
            //println("Point 5: " + s)
            s + ShoeCommon.SEPERATOR + s2;
          }
      }
    }
    println("Point 3")
    val prodTake5 = //take 5
      prodFold.mapTo(ShoeCommon.PROD_AND_RECO -> ShoeCommon.PROD_AND_RECO) {
        (pid: String, pr: String) =>
          {
            val reco = ShoeCommon.procStrSz(pr, pid, 5)
            if (debug) println("Product : " + pid + ", recommendations: " + reco)
            (pid, reco)
          }
      }
    if (debug) {
      println("price v5")
    }
    (prodTake5)
  }

  def pricePipeGet() = {
    val pricePipe2 = Tsv(args("inPrice"), ShoeCommon.PriceSchema).read.addTrap(Tsv(RPaths.PricePipeErrs))
    //average out min, max price. nulls are looked after by scala when parsing to Double
    val pricePipe = pricePipe2.mapTo(ShoeCommon.PriceSchema -> ShoeCommon.PriceAvg) {
      (productId: String, maxSalePrice: Double, min: Double) =>
        {
          var avg = (maxSalePrice + min) / 2;
          (productId, avg)
        }
    }
    if (debug) println("Price At 1")
    (pricePipe)
  }

}