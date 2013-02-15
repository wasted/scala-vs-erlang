package ag.bett.scala.test


case object Reset
case class AddCount(number:Long)


object BenchmarkAll extends App {
	val runs = 12000000

	override def main(args: Array[String]) {

		// lift
		println("Warmup run!")
		lift.Application.start(false)
		lift.Application.stop
		println("Warmup run finished!")
		val runtime = Runtime.getRuntime

		println("Garbage Collection")
		runtime.gc
		println

		// lift
		lift.Application.start()
		lift.Application.stop()

		println("Garbage Collection")
		runtime.gc
		println

		// akka
		akka.Application.start()
		akka.Application.stop()

		println("Garbage Collection")
		runtime.gc
		println

		//wactor 
		wactor.Application.start()
		wactor.Application.stop()

		println("Garbage Collection")
		runtime.gc
		println


		sys.exit(0)
	}
}

