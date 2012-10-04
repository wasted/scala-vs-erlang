package ag.bett.scala.test.lift

import _root_.scala.compat.Platform
import ag.bett.scala.test._

import net.liftweb.actor._
import java.util.concurrent.atomic._


object Application {
	val runs = 12000000
	val counter = new CounterActor

	def main(args: Array[String]) {
		start()
		stop()
		sys.exit(0)
	}

	def start(print: Boolean = true) { runTest(runs, print) }
	def stop() { LAScheduler.shutdown() }


	def runTest(msgCount: Long, print: Boolean) {
		val start = Platform.currentTime
		val count = theTest(msgCount)
		val finish = Platform.currentTime
		val elapsedTime = (finish - start) / 1000.0

		// disable output on warmup run!
		if (print) {
			printf("%n")
			printf("[lift] Count is %s%n",count)
			printf("[lift] Test took %s seconds%n", elapsedTime)
			printf("[lift] Throughput=%s per sec%n", msgCount / elapsedTime)
			printf("%n")
		}
	}

	def theTest(msgCount: Long): Any = {
		val bytesPerMsg = 100
		val updates = (1L to msgCount).par.foreach((x: Long) => counter ! new AddCount(bytesPerMsg))

		val count = (counter !! GetAndReset).open_!
		return count
	}

}


class CounterActor extends LiftActor {
	var count = 0L

	def messageHandler = {
		case GetAndReset =>
			val current = count
			count = 0
			reply(current)
		case AddCount(extraCount) =>
			count += extraCount
	}
}

