package shoes.m

import shoes.ShoeCommon
import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe
/**
 * @author Tushar
 */
class Reccommend3(args: Args) extends Job(args) {
  implicit def str2bool(string: String): Boolean = string == null || string.toUpperCase.equals("TRUE")

  var debug = true

  initInput()
  def initInput() {
    val products1 = Tsv(args("input"), shoes.ShoeCommon.ProductsSchema).read
    val catRecmnds = Tsv(args("inRCats"), shoes.ShoeCommon.CatRecoSchema).read //recomdsCats.csv
    val products = products1.filter('gender) {
      gender: String =>
        val g = gender + " " //make sure we have a non null string with length at least 1
        Character.toLowerCase(g.charAt(0)) == 'm'
    }.project('productId, 'primaryType_p1, 'type_p2, 'subType_p3)
    //shoes.m.PriceMerge.process(products, catRecmnds, args)
    val cats2 = catRecmnds.map('full_cat -> ('rt1, 'rt2, 'rt3)) {
      x: (String) =>
        {
          val (txt) = x
          val r: Array[String] = txt.split("_")
          (r(0), r(1), r(2))
        }
    }
    process(products, cats2)

  }

  def process(products: Pipe, cats2: Pipe) = {
    debug = args("debug")
    if (debug) println("1 to 3")

    val joinType = new LeftJoin
    val jointProdWithRecoCat = products.joinWithSmaller((('primaryType_p1, 'type_p2, 'subType_p3) -> ('rt1, 'rt2, 'rt3)),
      cats2, joinType).addTrap(Tsv("./o1/join_pro-rec_err.csv"))
    if (debug) {
      println("join with cat rec done")
      jointProdWithRecoCat.write(Tsv("./o1/prpodcust1AfterJoin1.csv"))
    }
    processQ1Match(jointProdWithRecoCat, cats2)
    processPrice(jointProdWithRecoCat, cats2)

  }

  def processQ1Match(jointProdWithRecoCat: Pipe, catRecmnds: Pipe) = {
    val n = jointProdWithRecoCat.map(('productId, 'RecommendedProductIds) -> ('MyRecomdProducts)) {
      (productId: String, recommendedProductIds: String) =>
        {
          (ShoeCommon.strToArrRemoveMatch(recommendedProductIds, productId))

        }

    } //.discard('RecommendedProductIds)

    n.write(Tsv("./o1/prpodcust1AfterJoin3.csv"))
    if (debug) println("done 7");

  }

  def processPrice(products: Pipe, catRecmnds: Pipe) = {

    val pricePipe2 = Tsv(args("inPrice"), shoes.ShoeCommon.PriceSchema).read

    val pricePipe = pricePipe2.mapTo(('productIdPrc, 'maxSalePrice, 'minSalePrice) -> ('productIdPrc, 'prc)) {
      (productId: String, maxSalePrice: Double, min: Double) =>
        {
          var mx = (maxSalePrice + min) / 2;
          (productId, mx)
        }
    }.addTrap(Tsv("./o1/prices1_err.csv"))
    pricePipe.write(Tsv("./o1/pricePipe2.csv"))
    if (debug) println("Price At 1")
    //val p2 = products.joinWithSmaller('productId, pricePipe, 'productIdPrc)
    if (debug) shoes.M.main2()
    val pr2 = products.flatMap(('RecommendedProductIds) -> ('prodReco)) {
      txt: String =>
        {
          if (txt == null) {
            Array.empty[String]
          } else {
            txt.split(",")
          }
        }
    }
    val joinType = new LeftJoin
    val pr3 = pr2.joinWithSmaller(('prodReco -> 'productIdPrc), pricePipe, joinType).addTrap(Tsv("./o1/join_price-prod_err.csv"))
    if (debug) pr3.write(Tsv("./o1/ProdpriceA.csv"))
    val pr4 = pr3.groupBy('productId) {
      _.sortBy('prc)
        .take(6)
    }
    if (debug) pr4.write(Tsv("./o1/Prodprice-B.csv"))
    val pr5 = pr4.groupBy('productId) {
        _.foldLeft(('prodReco) -> 'prodsR)("") {
          (s: String, s2: String) => { s + "," + s2; }
        }
    }
        //take 5
    val pr6 =
      pr5.map(('productId, 'prodsR) -> ('productId, 'prodsR)) {
        var b: Array[String] = Array()
        (pid: String, pr: String) =>
          {
            (pid, ShoeCommon.procStrSz(pr, pid, 5))
          }
      }.write(Tsv("./o1/Prodprice-C.csv"))
  }

}