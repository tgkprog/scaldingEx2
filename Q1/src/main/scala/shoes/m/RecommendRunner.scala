package shoes.m

import org.apache.hadoop.util.ToolRunner
import org.slf4j.LoggerFactory

import com.twitter.scalding.Args
import com.twitter.scalding.Tool

/**
 */
object RecommendRunner extends App {
  val runnerArgs = Args(args)
  val configuration = new org.apache.hadoop.conf.Configuration

  val log = LoggerFactory.getLogger(this.getClass.getName)

  //log.info("Executing [ProdRec] Job")
  //  ToolRunner.run(configuration, new Tool,
  //(classOf[ProdRec].getName :: runnerArgs.toList).toArray )

  log.info("Executing [ReccommendProductPrices] Job")
  ToolRunner.run(configuration, new Tool,
    (classOf[ReccommendProductPrices].getName :: runnerArgs.toList).toArray)

  second

  def second() = {
    //or just make a new Runner!
    val th: Thread = new Thread() {
      override def run() = {
        Thread.sleep(23000);
        log.info("Executing [ReccommendProducts] Job")
        ToolRunner.run(configuration, new Tool,
          (classOf[ReccommendProducts].getName :: runnerArgs.toList).toArray)
      }

    }
    th.start()

  }

}