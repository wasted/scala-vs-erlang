package ag.bett.scala.test


case object GetAndReset
case class AddCount(number:Long)


object BenchmarkAll extends App {
	val runs = 12000000

	override def main(args: Array[String]) {

		// lift
		println("Warmup run!")
		lift.Application.start(false)
		lift.Application.stop
		println("Warmup run finished!")

		// lift
		lift.Application.start()
		lift.Application.stop()

		// akka
		akka.Application.start()
		akka.Application.stop()

		// scala
		scala.Application.start()
		scala.Application.stop()

		sys.exit(0)
	}
}

