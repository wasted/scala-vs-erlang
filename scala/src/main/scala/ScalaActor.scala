package ag.bett.scala.test.scala

import _root_.scala.compat.Platform
import ag.bett.scala.test._

import _root_.scala.actors.Actor
import _root_.scala.actors.Actor._
import _root_.scala.actors.Scheduler


object Application {
	val runs = 12000000
	val counter = new CounterActor

	def main(args: Array[String]) {
		start()
		stop()
		sys.exit(0)
	}

	def start() { runTest(runs) }
	def stop() { Scheduler.shutdown }

	def runTest(msgCount: Long) {
		val start = Platform.currentTime
		val count = theTest(msgCount)
		val finish = Platform.currentTime
		val elapsedTime = (finish - start) / 1000.0

		printf("%n")
		printf("%n")
		printf("[scala] Count is %s%n",count)
		printf("[scala] Test took %s seconds%n", elapsedTime)
		printf("[scala] Throughput=%s per sec%n", msgCount / elapsedTime)
		printf("%n")
		printf("%n")
	}

	def theTest(msgCount: Long): Any = {
		val bytesPerMsg = 100
		val updates = (1L to msgCount).par.foreach((x: Long) => counter ! new AddCount(bytesPerMsg))

		val count = counter !? GetAndReset
		return count
	}
}


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

