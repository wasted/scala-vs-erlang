package ag.bett.scala.test.wactor

import _root_.scala.compat.Platform
import ag.bett.scala.test._

import io.wasted.util._
import java.util.concurrent.atomic._


object Application {
	val runs = 12000000
	val counter = new CounterWactor

	def main(args: Array[String]) {
		start()
		stop()
		sys.exit(0)
	}

	def start(print: Boolean = true) { runTest(runs, print) }
	def stop() { counter ! Wactor.Die }


	def runTest(msgCount: Long, print: Boolean) {
		val start = Platform.currentTime
		val count = theTest(msgCount)
		val finish = Platform.currentTime
		val elapsedTime = (finish - start) / 1000.0

		// disable output on warmup run!
		if (print) {
			printf("%n")
			printf("[lift] Test took %s seconds%n", elapsedTime)
			printf("[lift] Throughput=%s per sec%n", msgCount / elapsedTime)
			printf("%n")
		}
	}

	def theTest(msgCount: Long): Any = {
		val bytesPerMsg = 100
		val updates = (1L to msgCount).par.foreach((x: Long) => counter ! new AddCount(bytesPerMsg))
        counter ! Reset
	}

}

class CounterWactor extends Wactor {
	override val loggerName = "CounterWactor"
	var count = 0L

	def receive = {
		case Reset =>
			printf("[wactor] Count is %s%n",count)
			count = 0
		case AddCount(extraCount) =>
			count += extraCount
	}
}

