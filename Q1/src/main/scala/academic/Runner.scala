package academic

import com.twitter.scalding.{Tool, Args}
import org.slf4j.LoggerFactory
import org.apache.hadoop.util.ToolRunner
import shoes.Recmd1

/**
 */
object Runner extends App {

  val runnerArgs = Args(args)
  val configuration = new org.apache.hadoop.conf.Configuration

  val log = LoggerFactory.getLogger(this.getClass.getName)
  new academic.M
  log.info("Executing [Recmd1] Job")
  ToolRunner.run(configuration, new Tool,
    (classOf[Recmd1].getName :: runnerArgs.toList).toArray )

}
