package shoes
import shoes.m._
import com.twitter.scalding._
import org.scalatest._

//import org.junit.runner.RunWith
//import org.scalatest.junit.JUnitRunner
//@RunWith(classOf[JUnitRunner])

class PipeTests extends WordSpec with Matchers {
  // Import Dsl._ to get implicit conversions from List[Symbol] -> cascading.tuple.Fields etc
  import Dsl._

  val ProductsSchema = List('productId, 'brand, 'style, 'gender, 'primaryType_t1, 'type_t2, 'subType_t3, 'color)

  val expData = List(
    ("p1", "nikeb"), ("p2", "adib"))

  val prdtData = List(
    ("p1", "nike", "s1", "m", "run", "c2a", "fast", "blue"), ("p2", "adi", "s1", "f", "jog", "c2b", "summer", "pink"))

  var errData = "";
  val finalData = "";

  "Product brancd " should {
    JobTest("shoes.TestJob")
      .arg("input", "inputFile")

      .arg("output", "outputFile")
      .source(Tsv("inputFile", ProductsSchema), prdtData)
      .source(Tsv("./o1/prodsRead_err.csv"), errData)
      .sink[(String, String)](Tsv("outputFile")) {
        outputBuffer =>
          val result = outputBuffer
          "must merge products with prices & recommendations" in {
            result shouldEqual expData
          }
      }.run
      .finish
  }
}