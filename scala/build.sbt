import AssemblyKeys._

name := "scalavserlang"

organization := "ag.bett.scala"

version := "1.0"

scalaVersion := "2.9.1"

assemblySettings


resolvers ++= Seq(
  "Repo Maven" at "http://repo1.maven.org/maven2/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
  "Bryan J Swift Repository" at "http://repos.bryanjswift.com/maven2/",
  "snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "releases"        at "http://oss.sonatype.org/content/repositories/releases"
)

// if you have issues pulling dependencies from the scala-tools repositories (checksums don't match), you can disable checksums
//checksums := Nil

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-Xcheckinit", "-unchecked")

// Akka
libraryDependencies ++= {
  val akkaVersion = "2.0.2"
  Seq(
    "com.typesafe.akka" % "akka-actor" % akkaVersion,
    "com.typesafe.akka" % "akka-kernel" % akkaVersion
  )
}

// Base dependencies
libraryDependencies ++= Seq(
  "org.scala-tools.testing" % "specs_2.9.0" % "1.6.8" % "test", // For specs.org tests
  "net.liftweb" %% "lift-actor" % "2.4" % "compile", // For specs.org tests
  "junit" % "junit" % "4.8" % "test->default", // For JUnit 4 testing
  "ch.qos.logback" % "logback-classic" % "0.9.26" % "compile->default"
)


