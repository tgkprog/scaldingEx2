package academic

import com.twitter.scalding.{ Tool, Args }
import org.slf4j.LoggerFactory
import org.apache.hadoop.util.ToolRunner
import academic.TestJob

/**
 */

object TestRunner extends App {

  val runnerArgs = Args(args)
  val configuration = new org.apache.hadoop.conf.Configuration

  val log = LoggerFactory.getLogger(this.getClass.getName)
  new academic.M
  log.info("Executing [TestJob] Job")
  ToolRunner.run(configuration, new Tool,
    (classOf[TestJob].getName :: runnerArgs.toList).toArray)

}
