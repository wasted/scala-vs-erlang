package ag.bett.scala.test.akka

import _root_.scala.compat.Platform
import ag.bett.scala.test._

import _root_.akka.actor._
import _root_.akka.event.Logging
import _root_.akka.util._
import _root_.akka.util.duration._
import _root_.akka.dispatch._
import _root_.akka.pattern.ask


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
		val count = theTest(counter, msgCount)
		val finish = Platform.currentTime
		val elapsedTime = (finish - start) / 1000.0

		printf("%n")
		printf("[akka] Count is %s%n",count)
		printf("[akka] Test took %s seconds%n", elapsedTime)
		printf("[akka] Throughput=%s per sec%n", msgCount / elapsedTime)
		printf("%n")
	}

	implicit val timeout = Timeout(5 seconds)

	def theTest(counter: ActorRef, msgCount: Long) = {
		val bytesPerMsg = 100
		val updates = (1L to msgCount).par.foreach((x: Long) => counter ! new AddCount(bytesPerMsg))
		val future = counter ? GetAndReset

		Await.result(future, timeout.duration).asInstanceOf[Long]
	}

}


case object GetAndReset
case class AddCount(number:Long)

class CounterActor extends Actor {
	var count: Long = 0

	def receive = {
		case GetAndReset =>
			val current = count
			count = 0
			sender ! current
		case AddCount(extraCount) =>
			count=count+extraCount
	}
}

