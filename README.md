![wasted.io](http://wasted.io/images/soon/wasted.png)

## Scala vs Erlang Actors

Thanks to [Paul Keeble](https://github.com/PaulKeeble) for getting this benchmark project started a few years back.

Benchmark for testing Actor implementations on Scala and Erlang.

The Actor has 2 responsibilities:

- Add a count X to the Actors current tally
- Output the current count and reset the agent count to zero

## Usability of these benchmarks

We know that these benchmarks are not representative for 99% of actor use cases, the simple idea is to get number of msgs/sec put thru the actor without having it do too much.

## Erlang OTP

Compile the 3 files then run ```client:runTest(3000000).```

## Erlang bare receive version

Compile server2 as well as the 3 files for OTP and then run ```client:runTest2(3000000).```

## Scala

### Actors being used are Lift Actor, Akka and our own Wactor.

Simply run ```./sbt run``` and select one of the benchmarks.


## 2011 results on Scala 2.9.1 vs Erlang 5.8.5

I wrote [Scala, Akka and Erlang Actor Benchmarks](http://uberblo.gs/2011/12/scala-akka-and-erlang-actor-benchmarks) when i was really starting out with Scala.


