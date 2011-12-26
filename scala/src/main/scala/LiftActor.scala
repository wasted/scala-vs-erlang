package ag.bett.scala.test.lift

import net.liftweb.actor._
import scala.compat.Platform


case object GetAndReset
case class AddCount(number:Long)

object CounterActor extends LiftActor {
	var count: Long = 0

	def messageHandler = {
		case GetAndReset =>
			val current = count
			count = 0
			reply(current)
		case AddCount(extraCount) =>
			count=count+extraCount
	}
}

object CounterClient {
	val counter = CounterActor

	def run(runs: Long) {
		runTest(runs)
	}

	def runTest(msgCount: Long) {
		val start = Platform.currentTime
		val count = theTest(msgCount)
		val finish = Platform.currentTime
		val elapsedTime = (finish - start) / 1000.0

		printf("[lift] Count is %s%n",count)
		printf("[lift] Test took %s seconds%n", elapsedTime)
		printf("[lift] Throughput=%s per sec%n", msgCount / elapsedTime)
	}

	def theTest(msgCount: Long): Any = {
		val bytesPerMsg = 100
		val updates = (1L to msgCount).par.foreach((x: Long) => counter ! new AddCount(bytesPerMsg))

		val count = (counter !! GetAndReset).open_!
		return count
	}
}
