package shoes

import com.twitter.scalding._
import org.scalatest._

//import org.junit.runner.RunWith
//import org.scalatest.junit.JUnitRunner
//@RunWith(classOf[JUnitRunner])

class LoginGeoTest extends WordSpec with Matchers {
  // Import Dsl._ to get implicit conversions from List[Symbol] -> cascading.tuple.Fields etc
  import Dsl._

  val ProductsSchema = List('productId, 'brand, 'style, 'gender, 'primaryType_t1, 'type_t2, 'subType_t3, 'color)
  val CatRecoSchema = List('full_cat, 'RecommendedProductIds)

  val PriceSchema = List('productIdPrc, 'maxSalePrice, 'minSalePrice)
//
  val testShoeData = List(
    ("2014/07/01", "-", "login" , "-", "-" , "40.001,30.001" , "-", "PC", "-"),
    ("2014/07/01", "-", "login" , "-", "-" , "40.002,30.002" , "-", "PC", "-"))
    
    val prdtData = List(
("p1","nike","s1","m","run","c2a","fast","blue")
,("p2","nike","s1","f","jog","c2b","summer","pink")
,("p3","adidas","s2","f","run","c2a","tennis","white")
,("p4","puma","s3","m","walk","c2b","stroll","red")
,("p5","nike","s4","m","walk","c2c","saunter","pola blue yellow")
,("p6","adidas","s1","f","jog","c2a","summer","white")
,("p7","puma","s3","m","run","c2b","tennis","white")
,("p8","puma","s2","f","run","c2a","badminton","red")
,("p9","adidas","s1","m","jog","c2a","summer","white")
,("p10","adidas","s2","m","run","c2a","badminton","white")
,("p11","puma","s2","m","walk","c2a","office","black")
,("p12","nike","s1","f","jog","t2b","summer","blue")
,("p13","nike","s1","f","walk","elegant","office","black")
,("p14","nike","s1","f","walk","t2c","saunter","pink")
,("p15","adidas","s2","m","walk","t2c","saunter","white")
,("p16","puma","s3","m","walk","t2c","saunter","red")
,("p17","nike","s4","m","walk","t2c","saunter","pola blue yellow")
,("p18","nike","s1","m","jog","t2b","summer","blue")
,("p19","nike","s1","f","jog","t2b","summer","pink")
,("p20","adidas","s2","m","run","t2a","tennis","white")
,("p21","puma","s3","m","walk","t2b","stroll","red")
,("p22","nike","s4","m","walk","t2c","saunter","pola blue yellow")
,("p23","nike","s1","f","jog","t2b","summer","pink")
,("p24","adidas","s2","m","run","t2a","tennis","white")
,("p25","puma","s3","m","walk","t2b","stroll","red")
,("p26","nike","s4","m","jog","t2b","summer","pola blue yellow")
,("p27","nike","s1","m","run","t2a","fast","blue")
,("p28","nike","s1","f","jog","t2b","summer","pink")
,("p29","adidas","s2","m","run","t2a","tennis","white")
,("p30","puma","s3","m","jog","t2b","summer","red")
,("p31","nike","s4","m","walk","t2c","saunter","pola blue yellow1")
)

//
//  "The LoginGeo job" should {
//      JobTest("loganalysis.LoginGeo")
//        .arg("input", "inputFile")
//        .arg("output", "outputFile")
//        .source(Tsv("inputFile", schema), testData )
//        .sink[(String)](Tsv("outputFile")){
//           outputBuffer => val result = outputBuffer.mkString
//           "identify nearby login events and bucket them" in {
//             result shouldEqual s"""[{"lat":40.00,"lon":30.00,"device":"PC",count:2}]"""
//           }
//        }.run
//         .finish
//    }
}