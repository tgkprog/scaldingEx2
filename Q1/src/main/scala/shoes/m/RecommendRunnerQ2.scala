package shoes.m

import org.apache.hadoop.util.ToolRunner
import org.slf4j.LoggerFactory

import com.twitter.scalding.Args
import com.twitter.scalding.Tool

/**
 */
object RecommendRunnerQ2 extends App {
  val runnerArgs = Args(args)
  val configuration = new org.apache.hadoop.conf.Configuration

  val log = LoggerFactory.getLogger(this.getClass.getName)

  log.info("Executing [ReccommendProducts] Job")
  ToolRunner.run(configuration, new Tool,
    (classOf[ReccommendProducts].getName :: runnerArgs.toList).toArray)

}