package shoes

import com.twitter.scalding.{ Tool, Args }
import org.slf4j.LoggerFactory
import org.apache.hadoop.util.ToolRunner

/**
 */

object TestRunner extends App {

  val runnerArgs = Args(args)
  val configuration = new org.apache.hadoop.conf.Configuration

  val log = LoggerFactory.getLogger(this.getClass.getName)
  new shoes.M
  log.info("Executing [Test] Job")
  ToolRunner.run(configuration, new Tool,
    (classOf[TestJob].getName :: runnerArgs.toList).toArray)

}
