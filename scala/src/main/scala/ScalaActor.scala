package ag.bett.scala.test.scala

import scala.actors.Actor
import scala.actors.Actor._
import scala.compat.Platform
import scala.actors.Scheduler
import akka.kernel._


class ActorKernel extends Bootable {
	val runs = 12000000

	def startup = {
		ag.bett.scala.test.scala.CounterClient.run(runs)
		shutdown
	}

	def shutdown = sys.exit(0)

}


case object GetAndReset
case class AddCount(number:Long)

class CounterActor extends Actor {
	var count: Long = 0

	def act() {
		react {
			case GetAndReset =>
				val current = count
				count = 0
				reply(current)
				act
			case AddCount(extraCount) =>
				count=count+extraCount
				act
		}
	}
	start()
}

object CounterClient {
	val counter = new CounterActor

	def run(runs: Long) {
		runTest(runs)
		Scheduler.shutdown
	}

	def runTest(msgCount: Long) {
		val start = Platform.currentTime
		val count = theTest(msgCount)
		val finish = Platform.currentTime
		val elapsedTime = (finish - start) / 1000.0

		printf("[scala] Count is %s%n",count)
		printf("[scala] Test took %s seconds%n", elapsedTime)
		printf("[scala] Throughput=%s per sec%n", msgCount / elapsedTime)
	}

	def theTest(msgCount: Long): Any = {
		val bytesPerMsg = 100
		val updates = (1L to msgCount).par.foreach((x: Long) => counter ! new AddCount(bytesPerMsg))

		val count = counter !? GetAndReset
		return count
	}
}
