package com.kohls.recommendations

import com.kohls.recommendations.ProductCommon.debug
import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe
//avShoeCommon
class PipeCommon(args: Args) extends Job(args) {
  ProductCommon.init(args)
  val (products, cats) = initInput(args)
  def initInput(args: Args): (Pipe, Pipe) = {

    val products1: Pipe = Tsv(args("input"), ProductCommon.ProductsSchema).read //productPipe naming convention
    val catRecmnds: Pipe = Tsv(args("inRCats"), ProductCommon.CatRecoSchema).read //recomdsCats.csv
    val products = products1.filter(ProductCommon.gender) {
      (pid: String, gender: String) =>
        {
          val isMale = (gender != null && gender.length() > 0 && Character.toLowerCase(gender.charAt(0)) == 'm')
          if (debug) println("Gender filter " + pid + " " + gender + " is " + isMale)
          //make sure we have a non null string with length at least 1
          isMale
        }
    }.project(ProductCommon.prodIdTypes)

    val cats2 = catRecmnds.mapTo(ProductCommon.CatRecoSchema -> ProductCommon.CAT_TYPES_RECO_IDs) {
      x: (String, String) =>
        {
          val (txt, reco) = x
          if (txt == null || txt.length() == 0) ("", "", "", reco) else { //assume if not null its good data
            val r: Array[String] = txt.split("_")
            (r(0), r(1), r(2), reco)
          }
        }
    }
    (products, cats2)
  }

  def writeOutProd(prods: Pipe, fileName: String, cats: Pipe = null) = {
    if (debug) prods.debug
    prods.write(Tsv(fileName))
    (prods)
  }
}//
