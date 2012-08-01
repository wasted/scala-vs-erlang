name := "scalavserlang"

organization := "ag.bett.scala"

version := "2.0"

scalaVersion := "2.9.1"

resolvers ++= Seq(
  "Repo Maven" at "http://repo1.maven.org/maven2/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

// if you have issues pulling dependencies from the scala-tools repositories (checksums don't match), you can disable checksums
//checksums := Nil

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-Xcheckinit", "-unchecked")

// Base dependencies
libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor" % "2.0.2",
  "org.scala-tools.testing" % "specs_2.9.0" % "1.6.8" % "test", // For specs.org tests
  "net.liftweb" %% "lift-actor" % "2.4" % "compile", // For specs.org tests
  "junit" % "junit" % "4.8" % "test->default", // For JUnit 4 testing
  "ch.qos.logback" % "logback-classic" % "0.9.26" % "compile->default"
)


