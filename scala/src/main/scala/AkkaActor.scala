package io.wasted.bench.scalavserlang.akka

import _root_.scala.compat.Platform
import io.wasted.bench.scalavserlang._

import _root_.akka.actor._
import _root_.akka.util._
import scala.concurrent.duration._


object Application {
	val system = ActorSystem("AkkaTest")
	val testActor = system.actorOf(Props[CounterActor], "test")
	val runs = 12000000

	def main(args: Array[String]) {
		start()
		stop()
		sys.exit(0)
	}

	def start() { runTest(testActor, runs) }
	def stop() { system.shutdown() }

	def runTest(counter: ActorRef, msgCount: Long) {
		val start = Platform.currentTime
		theTest(counter, msgCount)
		val finish = Platform.currentTime
		val elapsedTime = (finish - start) / 1000.0

		printf("%n")
		printf("[akka] Count is %s%n", msgCount)
		printf("[akka] Test took %s seconds%n", elapsedTime)
		printf("[akka] Throughput=%s per sec%n", msgCount / elapsedTime)
		printf("%n")
	}

	def theTest(counter: ActorRef, msgCount: Long) = {
		val bytesPerMsg = 100
		val updates = (1L to msgCount).par.foreach((x: Long) => counter ! new AddCount(bytesPerMsg))

		counter ! Reset
	}

}

class CounterActor extends Actor {
	var count: Long = 0

	def receive = {
		case Reset =>
			printf("[akka] Count is %s%n",count)
			count = 0
		case AddCount(extraCount) =>
			count=count+extraCount
	}
}

