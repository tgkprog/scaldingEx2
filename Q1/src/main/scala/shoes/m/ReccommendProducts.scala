package shoes.m

import shoes.ShoeCommon
import shoes.ShoeCommon.debug
import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe
import shoes.PipeCommon
/**
 * @author Tushar
 */
class ReccommendProducts(args: Args) extends PipeCommon(args) {

  val prods = productJoinWithRecommendations(products, cats)
  writeOutProd(prods, "./o1/pr-podcustAfterJoin3.csv")

  /**
   * Task 1 : match up products with recommendations
   */
  def productJoinWithRecommendations(products: Pipe, cats2: Pipe) = {

    if (debug) println("1 to 3 g")

    val joinType = new LeftJoin
    val jointProdWithRecoCat = products.joinWithSmaller((('primaryType_t1, 'type_t2, 'subType_t3) -> ('rt1, 'rt2, 'rt3)),
      cats2, joinType).addTrap(Tsv("./o1/join_pro-rec_err.csv")).discard('primaryType_t1, 'type_t2, 'subType_t3)
    if (debug) {
      println("join with cat rec done")
      jointProdWithRecoCat.write(Tsv("./o1/prpodcust1AfterJoin1.csv"))
    }
    //remove self product id from recommendations and keep 5
    val prodWithReco = jointProdWithRecoCat.map(('productId, 'RecommendedProductIds) -> ('MyRecomdProducts)) {
      (productId: String, recommendedProductIds: String) =>
        {
          (ShoeCommon.procStrSz(recommendedProductIds, productId, 5))
        }
    }
    if (debug) println("done 7");
    (prodWithReco)
  }



}