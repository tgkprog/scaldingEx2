package com.kohls.recommendations.impl

import com.kohls.recommendations.ProductCommon
import com.kohls.recommendations.ProductCommon.debug
import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe
import com.kohls.recommendations.PipeCommon
import com.kohls.recommendations.RPaths
/**
 * @author Tushar
 */
class ReccommendProducts(args: Args) extends PipeCommon(args) {

  val prods = productJoinWithRecommendations(products, cats)
  writeOutProd(prods, RPaths.finalProdRecoCat)

  /**
   * Task 1 : match up products with recommendations
   */
  def productJoinWithRecommendations(products: Pipe, cats2: Pipe) = {
    if (debug) println("1 to 3 prod reco")
    val joinType = new LeftJoin
    val jointProdWithRecoCat = products.joinWithSmaller((ProductCommon.CAT_TYPES_PROD -> ProductCommon.CAT_TYPES_RECO),
      cats2, joinType).addTrap(Tsv(RPaths.ProdCatErrs)).discard(ProductCommon.CAT_TYPES_PROD)
    if (debug) {
      println("join with cat rec done")
      jointProdWithRecoCat.write(Tsv(RPaths.prodAfterCatJoin))
    }
    //remove self product id from recommendations and keep 5
    val prodWithReco = jointProdWithRecoCat.mapTo(ProductCommon.PROD_AND_RECO -> ProductCommon.PROD_AND_RECO) {
      (productId: String, recommendedProductIds: String) =>
        {
          (productId, ProductCommon.procStrSz(recommendedProductIds, productId, 5))
        }
    }
    if (debug) println("done ReccommendProducts");
    (prodWithReco)
  }



}