package shoes
import scala.collection.mutable
import shoes.m._
import com.twitter.scalding._
import org.scalatest._

//import org.junit.runner.RunWith
//import org.scalatest.junit.JUnitRunner
//@RunWith(classOf[JUnitRunner])

class Price5Test extends WordSpec with Matchers {
  /* Import Dsl._ to get implicit conversions from List[Symbol] -> cascading.tuple.Fields etc */
  import Dsl._

  val ProductsSchema = List('productId, 'brand, 'style, 'gender, 'primaryType_t1, 'type_t2, 'subType_t3, 'color)
  val CatRecoSchema = List('full_cat, 'RecommendedProductIds)

  val PriceSchema = List('productIdPrc, 'maxSalePrice, 'minSalePrice)
  val t2 = List('full_cat, 'RecommendedProductIds)

  val testShoeData = List(
    ("2014/07/01", "-", "login", "-", "-", "40.001,30.001", "-", "PC", "-"),
    ("2014/07/01", "-", "login", "-", "-", "40.002,30.002", "-", "PC", "-"))

  val (prdtData, catsData, prcData) = RecommendTestsDataInit.getObjs1()
  val (recoDataExp) = shoes.RecommendTestsDataInit.getExpected1();
  val errData = "";
  val dbgData = "";
  val finalData = ""

  "The Top 5 Recommendations by Price job v5" should {
    JobTest("shoes.m.ReccommendProductPrices")
      .arg("input", "input")
      .arg("inRCats", "inRCats")
      .arg("inPrice", "inPrice")
      .arg("debug", "true")
      //.arg("--debug", "false")
      .arg("output", "output")
      .source(Tsv("input", ProductsSchema), prdtData)
      .source(Tsv("inRCats", CatRecoSchema), catsData)
      .source(Tsv("inPrice", PriceSchema), prcData)
      .source(Tsv("./o1/prodsRead_err.csv"), errData)
      .source(Tsv("./o1/cats_err.csv"), errData)
      .source(Tsv("./o1/prodsRead_err.csv"), errData)
      .source(Tsv("./o1/join_pro-pric-rec_err.csv"), errData)
      .source(Tsv("./o1/join_price-prod_err.csv"), errData)
      .source(Tsv("./o1/pricesRead2_err.csv"), errData)
      .source(Tsv("./o1/pricePipe2.csv"), errData)
      .source(Tsv("./o1/ProdpriceA.csv"), errData)
      .source(Tsv("./o1/catsOut.csv", t2), errData)
      .source(Tsv("./o1/Prodprice-B.csv"), errData)
      .sink[String](Tsv("./o1/pricePipepr6.csv")){
       outputBuffer   =>
          val result = outputBuffer
          "pricePipepr6" in {
            println("pricePipepr6 " + result)
            //result shouldEqual recoDataExp
          }
    }
      
      //: mutable.Buffer[(String, String)]
      .sink[(String)](Tsv("output")) {
        outputBuffer   =>
          val result = outputBuffer
          "must merge products with prices & recommendations" in {
            println("errData  " + errData + "\n dbgData " + dbgData + "\n")
            println("result1 " + result)
            //result shouldEqual recoDataExp
          }
      }.run
      .finish
  }
}