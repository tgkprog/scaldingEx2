package shoes
import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe
/**
 * @author t
 */
class Recmd1(args: Args) extends Job(args) {

  //  val logs = IterableSource[(String,String,String,String,String,String,String,String,String)](input, ('datetime, 'user, 'activity, 'data,
  //    'session, 'location, 'response, 'device))

  val products1 = Tsv(args("input"), shoes.Types.ProductsSchema).read
  val catRecmnds = Tsv(args("inRCats"), shoes.Types.CatRecoSchema).read //recomdsCats.csv
  process(products1, catRecmnds)

  def process(products1: Pipe, catRecmnds: Pipe) = {
    /**
     * x:(String,Int) =>
     * val (action, duration) = x
     */

    val products = products1.filter('gender) {
      gender: String =>
        val g = gender + " " //make sure we have a non null string with length at least 1
        g.toLowerCase().charAt(0) == 'm'
    }.map(('primaryType_p1, 'type_p2, 'subType_p3) -> ('catFul)) {
      x: (String, String, String) =>
        x._1.concat("_").concat(x._2).concat("_").concat(x._3)
    }
    val cats = products.unique('primaryType_p1, 'type_p2, 'subType_p3) //TODO .fitler('gender =)
    println("cats made 2");
    // val productsJoin = products.joinWithLarger(fs, that, joiner, reducers);
    cats.write(Tsv("./o1/cats.csv"))
    println("cats wrote")
    products.write(Tsv("./o1/prpodcust1.csv"))
    println("write prod debug 1 ./o1/prpodcust1.csv")
    val joinType = new LeftJoin
    val jointProdWithRecoCat = products.joinWithSmaller(('catFul -> 'R_catFul), catRecmnds, joinType).addTrap(Tsv("./o1/join_pro-rec_err.csv"))
    println("join with cat rec done")
    jointProdWithRecoCat.write(Tsv("./o1/prpodcust1AfterJoin1.csv"))
    processQ1Type1Match(jointProdWithRecoCat, catRecmnds)
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

    }
    n.write(Tsv("./o1/prpodcust1AfterJoin2.csv"))
    val n2 = n.project('productId, 'brand, 'style, 'gender, 'primaryType_p1, 'type_p2, 'subType_p3, 'MyRecomdProducts);
    n2.write(Tsv("./o1/prpodcust1AfterJoin3.csv"))
    println("done 7");
    try {
      //Thread.sleep(19000)  
    } catch {
      case t: Throwable => t.printStackTrace() // TODO: handle error
    }

    //System.exit(0);
  }
}