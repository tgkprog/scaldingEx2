package shoes.m

import com.twitter.scalding.{ Tool, Args }
import org.slf4j.LoggerFactory
import org.apache.hadoop.util.ToolRunner

/**
 */
object RecommendRunner extends App {
  val runnerArgs = Args(args)
  val configuration = new org.apache.hadoop.conf.Configuration

  val log = LoggerFactory.getLogger(this.getClass.getName)

  //log.info("Executing [ProdRec] Job")
  //  ToolRunner.run(configuration, new Tool,
  //(classOf[ProdRec].getName :: runnerArgs.toList).toArray )

  log.info("Executing [Reccommend3] Job")
  ToolRunner.run(configuration, new Tool,
    (classOf[Reccommend3].getName :: runnerArgs.toList).toArray)

}