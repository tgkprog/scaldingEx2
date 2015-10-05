package shoes.m
import com.twitter.scalding._
import cascading.pipe.joiner.LeftJoin
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe

class PriceMerge(args: Args) extends Job(args) {

  def processPrice(products: Pipe, catRecmnds: Pipe) = {
    //val pricePipe = Tsv(prcFile, shoes.Types.priceSchema).read
    //val products1 = Tsv(args("input"), shoes.Types.ProductsSchema).read
    val pricePipe2 = Tsv(args("inPrice"), shoes.Types.PriceSchema).read

    val pricePipe = pricePipe2.mapTo(('productIdPrc, 'maxSalePrice, 'minSalePrice) -> ('productIdPrc, 'prc)) {
      (productId: String, maxSalePrice: Double, min: Double) =>
        {
          var mx = maxSalePrice;
          var mi = min;
          
          mx = (mx + mi) / 2
          (productId, mx)
        }
    }.addTrap(Tsv("./o1/prices1_err.csv"))
    pricePipe.write(Tsv("./o1/pricePipe2.csv"))
    println("price done")
    //val p2 = products.joinWithSmaller('productId, pricePipe, 'productIdPrc)
    shoes.M.main2()

  }

}