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
    val jointProdWithRecoCat = products.joinWithSmaller((('primaryType_t1, 'type_t2, 'subType_t3) -> ('rt1, 'rt2, 'rt3)),
      catRecmnds, joinType).addTrap(Tsv("./o1/join_pro-pric-rec_err.csv")).discard('primaryType_t1, 'type_t2, 'subType_t3)
    val pricePipe = pricePipeGet()
    val pr2 = jointProdWithRecoCat.flatMap(('RecommendedProductIds) -> ('prodReco)) {
      txt: String =>
        {
          if (txt == null) {
            Array.empty[String]
          } else {
            txt.split(",")
          }
        }
    }
    val pr3 = pr2.joinWithSmaller(('prodReco -> 'productIdPrc), pricePipe, joinType).addTrap(Tsv("./o1/join_price-prod_err.csv"))
    ///if (debug) pr3.write(Tsv("./o1/ProdpriceA.csv"))
    val pr4 = pr3.groupBy('productId) { _.sortBy('prc).take(6) }
    ///if (debug) pr4.write(Tsv("./o1/Prodprice-B.csv"))
    //need to refer to productId here so we can filter it out from recommendations during fold.
    val pr5 = pr4.groupBy('productId) {
      _.foldLeft(('prodReco) -> 'RecommendedProductIds)("") {
        (s: String, s2: String) =>
          {
            //println("Point 5: " + s)
            s + "," + s2;
          }
      }
    }
    println("Point 3")
    val pr6 = //take 5
      pr5.mapTo(('productId, 'RecommendedProductIds) -> ('productId, 'RecommendedProductIds)) {
        (pid: String, pr: String) =>
          {
            val reco = ShoeCommon.procStrSz(pr, pid, 5)
            if (debug) println("Product : " + pid + ", recommendations: " + reco)
            (pid, reco)
          }
      }
    if (debug) pr6.write(Tsv("./o1/pricePipepr6.csv"))
    if (debug) {
      println("price v5")
    }
    (pr6)
  }

  def pricePipeGet() = {
    val pricePipe2 = Tsv(args("inPrice"), shoes.ShoeCommon.PriceSchema).read.addTrap(Tsv("./o1/pricesRead2_err.csv"))
    //average out min, max price. nulls are looked after by scala when parsing to Double
    val pricePipe = pricePipe2.mapTo(('productIdPrc, 'maxSalePrice, 'minSalePrice) -> ('productIdPrc, 'prc)) {
      (productId: String, maxSalePrice: Double, min: Double) =>
        {
          var avg = (maxSalePrice + min) / 2;
          (productId, avg)
        }
    } //.addTrap(Tsv("./o1/prices1_err.csv"))
    
    if (debug) println("Price At 1")
    (pricePipe)
  }

}