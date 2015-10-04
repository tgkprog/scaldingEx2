package shoes.m
import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe
/**
 * @author t
 */
class Reccommend2(args: Args) extends Job(args) {
  implicit def str2bool(string: String): Boolean = string == null || string.toUpperCase.equals("TRUE")

  var debug = true
  var aa: Any
  //  val logs = IterableSource[(String,String,String,String,String,String,String,String,String)](input, ('datetime, 'user, 'activity, 'data,
  //    'session, 'location, 'response, 'device))
  step1()
  def step1() {
    val products1 = Tsv(args("input"), shoes.Types.ProductsSchema).read
    val catRecmnds = Tsv(args("inRCats"), shoes.Types.CatRecoSchema).read //recomdsCats.csv
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
    println("1 to 3")

    val joinType = new LeftJoin
    val jointProdWithRecoCat = products.joinWithSmaller((('primaryType_p1, 'type_p2, 'subType_p3) -> ('rt1, 'rt2, 'rt3)),
      cats2, joinType).addTrap(Tsv("./o1/join_pro-rec_err.csv"))
    if (debug) {
      println("join with cat rec done")
      jointProdWithRecoCat.write(Tsv("./o1/prpodcust1AfterJoin1.csv"))
    }
    processQ1Type1Match(jointProdWithRecoCat, cats2)
    processPrice(jointProdWithRecoCat, cats2)

  }

  def processQ1Type1Match(jointProdWithRecoCat: Pipe, catRecmnds: Pipe) = {
    val n = jointProdWithRecoCat.map(('productId, 'RecommendedProductIds) -> ('MyRecomdProducts)) {
      //x : (String, String) => val(productId, RecommendedProductIds) = x ("bb","v")
      (productId: String, RecommendedProductIds: String) =>
        {
          var myp = "";
          var recom = RecommendedProductIds
          try {

            if (recom == null) recom = "";
            val i = recom.indexOf(productId)
            //string sub string or tokenize to list and remove depending on data quality and end format          
            if (i < 0) { //"p2,p6" "p2", "p6"
              myp = recom
            } else {
              var k = -1
              if (recom.length() > (i + productId.length())) {
                k = recom.indexOf(",", i) + 1
              } else {
                k = i + productId.length() + 1
              }
              if (k < 0 || k >= (recom.length())) k = i + productId.length()
              if (k < recom.length()) {
                myp = recom.substring(0, i) + recom.substring(k)
              } else {
                myp = recom.substring(0, i)
              }
            }
            myp = myp.trim();
            if (myp.endsWith(",")) {
              myp = myp.substring(0, myp.length() - 1)
            }
          } catch {
            case t: Throwable =>
              t.printStackTrace()
              myp = recom + "," + t
          }
          (myp)
        }

      /*  v*/

    }.discard('RecommendedProductIds)

    n.write(Tsv("./o1/prpodcust1AfterJoin3.csv"))
    println("done 7");
    try {
      //Thread.sleep(19000)  
    } catch {
      case t: Throwable => t.printStackTrace() // TODO: handle error
    }

    //
  }

  def processPrice(products: Pipe, catRecmnds: Pipe) = {
    //val pricePipe = Tsv(prcFile, shoes.Types.priceSchema).read
    //val products1 = Tsv(args("input"), shoes.Types.ProductsSchema).read
    val pricePipe2 = Tsv(args("inPrice"), shoes.Types.PriceSchema).read

    val pricePipe = pricePipe2.mapTo(('productIdPrc, 'maxSalePrice, 'minSalePrice) -> ('productIdPrc, 'prc)) {
      (productId: String, maxSalePrice: Double, min: Double) =>
        {
          var mx = maxSalePrice;
          var mi = min;
          if (mx == null) mx = 0
          if (mi == null) mi = 0
          mx = (mx + mi) / 2
          (productId, mx)
        }
    }.addTrap(Tsv("./o1/prices1_err.csv"))
    pricePipe.write(Tsv("./o1/pricePipe2.csv"))
    println("price done")
    //val p2 = products.joinWithSmaller('productId, pricePipe, 'productIdPrc)
    shoes.M.main2()
    val pr2 = products.flatMap(('RecommendedProductIds) -> ('prodReco)) {
      txt: String => txt.split(",")
    }
    val joinType = new LeftJoin
    val pr3 = pr2.joinWithSmaller(('prodReco -> 'productIdPrc), pricePipe, joinType).addTrap(Tsv("./o1/join_price-prod_err.csv"))
    pr3.write(Tsv("./o1/ProdpriceA.csv"))
    val pr4 = pr3.groupBy('productId) { _.sortBy('prc) } //.reverse
      .write(Tsv("./o1/ProdpriceB.csv"))

    val pr5 = pr4.groupBy(('productId)) { //_.sortBy('prc)//      
      //print(_)
      //val ss  :String = _.groupFields.iterator().next()
      
      //       println("" +  _._1 )
      _.foldLeft(('prodReco) -> 'prodsR)("") {
        (s: String, s2: String) =>
          {
            
            val currentProdId: String = "" //TODO get the current product id
            println(" s " + s + ", s2 :" + s2 + "; pid :" + currentProdId + ".")
            if (currentProdId.equals(s2)) {
              s
            } else {
              if (s.length() == 0) {
                s2
              } else {
                s + "," + s2;
              }
            }

          }

      }

    }.write(Tsv("./o1/Prodprice-C.csv"))

    pr5.groupBy('productId) {
      _.take(5)
    }.write(Tsv("./o1/Prodprice-D.csv"))

  }
}