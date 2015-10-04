package shoes.m

import com.twitter.scalding.{ Tool, Args }
import org.slf4j.LoggerFactory
import org.apache.hadoop.util.ToolRunner

/**
 */
object RecommendRunner extends App {

  
  //testing for string split and return tuple
  aa()
  def aa() {
    val txt = "a_v_b"
    //expect at least two under scores
    val r: Array[String] = txt.split("_")
    println(r(0) + "|  " + r(1) + "|  " + r(2))
    (r(0), r(1), r(2))
  }

  val runnerArgs = Args(args)
  val configuration = new org.apache.hadoop.conf.Configuration

  val log = LoggerFactory.getLogger(this.getClass.getName)

  //log.info("Executing [ProdRec] Job")
  //  ToolRunner.run(configuration, new Tool,
  //(classOf[ProdRec].getName :: runnerArgs.toList).toArray )

  log.info("Executing [Reccommend2] Job")
  ToolRunner.run(configuration, new Tool,
    (classOf[Reccommend2].getName :: runnerArgs.toList).toArray)
    
   

}