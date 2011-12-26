package ag.bett.scala.test.akka

import scala.compat.Platform
import akka.actor._
import akka.kernel._
import akka.event.Logging
import akka.util._
import akka.util.duration._
import akka.dispatch._


class AkkaActorKernel extends Bootable {
	val system = ActorSystem("AkkaTest")
	val runs = 12000000

	def startup = {
		val testActor = system.actorOf(Props[CounterActor], "test")
		runTest(testActor, runs)

		printf("--%n")
		ag.bett.scala.test.lift.CounterClient.run(runs)
		
		printf("--%n")
		ag.bett.scala.test.scala.CounterClient.run(runs)
		
		shutdown
		sys.exit(0)
		//system.eventStream.subscribe(mgmt, classOf[RemoteLifeCycleEvent])
	}

	def shutdown = {
		system.shutdown()
	}

	def runTest(counter: ActorRef, msgCount: Long) {
		val start = Platform.currentTime
		val count = theTest(counter, msgCount)
		val finish = Platform.currentTime
		val elapsedTime = (finish - start) / 1000.0

		printf("[akka] Count is %s%n",count)
		printf("[akka] Test took %s seconds%n", elapsedTime)
		printf("[akka] Throughput=%s per sec%n", msgCount / elapsedTime)
	}

	implicit val timeout = Timeout(5 seconds)

	def theTest(counter: ActorRef, msgCount: Long) = {
		val bytesPerMsg = 100
		val updates = (1L to msgCount).par.foreach((x: Long) => counter ! new AddCount(bytesPerMsg))

		Await.result(counter ? GetAndReset, timeout.duration).asInstanceOf[Long]
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

