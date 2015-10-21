package shoes

import shoes.ShoeCommon.debug
import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe
//av
class PipeCommon(args: Args) extends Job(args) {
  //if (debug) ShoeCommon.printEnvInfo()
  val (products, cats) = initInput(args)
  def initInput(args: Args): (Pipe, Pipe) = {

    val products1 = Tsv(args("input"), ShoeCommon.ProductsSchema).read
    val catRecmnds = Tsv(args("inRCats"), ShoeCommon.CatRecoSchema).read //recomdsCats.csv
    val products = products1.filter('gender) {
      gender: String =>
        val g = gender + " " //make sure we have a non null string with length at least 1
        Character.toLowerCase(g.charAt(0)) == 'm'
    }.project('productId, 'primaryType_t1, 'type_t2, 'subType_t3)

    val cats2 = catRecmnds.mapTo(('full_cat, 'RecommendedProductIds) -> ('rt1, 'rt2, 'rt3, 'RecommendedProductIds)) {
      x: (String, String) =>
        {
          val (txt, reco) = x
          val r: Array[String] = txt.split("_")
          (r(0), r(1), r(2), reco)
        }
    }
    (products, cats2)
  }

  def writeOutProd(prods: Pipe, fileName: String, cats : Pipe = null) = {
    val p2 = prods.discard('RecommendedProductIds).write(Tsv(fileName)).debug
    try {
      println(fileName + " filename2, 22, p2 :" + p2)
      if(cats != null){
        cats.write(Tsv("./o1/catsOut.csv"))
      }
//      println(prods.getParent.getConfigDef)
//      println(prods.getStepConfigDef.getAllKeys)
    } catch {
      case t: Throwable => println("err " + t);
    }
    (p2)
  }
}//
