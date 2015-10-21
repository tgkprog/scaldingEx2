package academic

import com.twitter.scalding._
import com.twitter.scalding.FunctionImplicits._
import cascading.pipe.Pipe
import shoes.ShoeCommon

class TestJob(args: Args) extends Job(args) {
  val products: Pipe = initInput(args).write(Tsv(args("output")))
   println("p4 TestJob");
  def initInput(args: Args): Pipe = {

    val products1 = Tsv(args("input"), ShoeCommon.ProductsSchema).read.addTrap(Tsv("./o1/prodsRead_err.csv"))
    println("p0 TestJob");
    val p2 = products1.mapTo(('productId, 'brand) -> ('p1, 'b)) {
      x: (String, String) =>
        {
          val (p, b) = x
          val b2 = b + "b"
          (p, b2)
        }
    }
    println("p3 TestJob");
    p2
  }

}