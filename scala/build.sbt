import AssemblyKeys._

name := "scalavserlang"

organization := "io.wasted.bench"

version := "2.0"

scalaVersion := "2.10.0"

assemblySettings

resolvers ++= Seq(
  "wasted.io/repo" at "http://repo.wasted.io/mvn",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Repo Maven" at "http://repo1.maven.org/maven2/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

// if you have issues pulling dependencies from the scala-tools repositories (checksums don't match), you can disable checksums
//checksums := Nil

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-Xcheckinit", "-unchecked", "-feature", "-optimise")

// Base dependencies
libraryDependencies ++= Seq(
  "io.wasted" %% "wasted-util" % "0.5.0-SNAPSHOT",
  "net.liftweb" %% "lift-actor" % "2.5-SNAPSHOT",
  "com.typesafe.akka" %% "akka-actor" % "2.1.0",
  "ch.qos.logback" % "logback-classic" % "0.9.26" % "compile->default"
)


