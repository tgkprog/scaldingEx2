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
 * Task 2 : Join products with prices and recommendations
 * Sort by recommended price and retain 5
 */
class ReccommendProductPrices2(args: Args) extends PipeCommon(args) {
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
    val joinType = new LeftJoin // LeftJoin, InnerJoin

    val pricePipe = pricePipeGet()
    val catRecFlat = catRecmnds.flatMap(ProductCommon.RECO_PROD_IDS -> ProductCommon.RECO_PROD_ID) {
      txt: String =>
        {
          if (debug) {
            println("flat map : " + txt)
          }
          if (txt == null) {
            Array.empty[String]
          } else {
            txt.split(ProductCommon.SEPERATOR)
          }
        }
    }
    catRecFlat.write(Tsv("./o1/catRecFlatF.csv"))
    val catFlatPricePipe = catRecFlat.joinWithLarger((ProductCommon.RECO_PROD_ID -> ProductCommon.PROD_ID_PRC), pricePipe, joinType).addTrap(Tsv(RPaths.catJoinPricePrdErr))
    catFlatPricePipe.write(Tsv("./o1/catFlatPricePipe.csv"))
    val prodPrcPids = products.joinWithSmaller((ProductCommon.CAT_TYPES_PROD -> ProductCommon.CAT_TYPES_RECO), catFlatPricePipe, joinType).addTrap(Tsv(RPaths.JoinPricePrdErr))
    ///if (debug) pr3.write(Tsv("./o1/ProdpriceA.csv"))
    val prodPrcGroped = prodPrcPids.groupBy(ProductCommon.Prod_Id) { _.sortBy(ProductCommon.price).reverse.take(6) }
    if (debug) prodPrcGroped.debug
    //need to refer to productId here so we can filter it out from recommendations during fold.
    val prodFold = prodPrcGroped.groupBy('productId) {
      _.foldLeft((ProductCommon.RECO_PROD_ID) -> ProductCommon.RECO_PROD_IDS)("") {
        (s: String, s2: String) =>
          {
            ProductCommon.strConcatIfNotNull(s, s2)
          }
      }
    }
    if(debug)println("Point 3")
    val prodTake5 = //take 5
      prodFold.mapTo(ProductCommon.PROD_AND_RECO -> ProductCommon.Prod_Id) {
        (pid: String, pr: String) =>
          {
            val reco = ProductCommon.procStrSz(pr, pid, 5)
            if (debug) println("Product : " + pid + ", recommendations: " + reco)
            (pid + "|" + reco)
          }
      }
    if (debug) {
      println("price v5")
    }
    (prodTake5)
  }

  def pricePipeGet() = {
    val pricePipe2 = Tsv(args("inPrice"), ProductCommon.PriceSchema).read.addTrap(Tsv(RPaths.PricePipeErrs))
    //average out min, max price. nulls are looked after by scala when parsing to Double
    val pricePipe = pricePipe2.mapTo(ProductCommon.PriceSchema -> ProductCommon.PriceAvg) {
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