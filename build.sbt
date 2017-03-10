import spray.revolver.RevolverPlugin._

seq(Revolver.settings: _*)

releaseSettings

scalariformSettings

organization := "co.s4n"

name := "cafetera"

scalaVersion := "2.12.1"

resolvers ++= Seq(
  "releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0" withSources() withJavadoc(),
  "com.iheart" %% "ficus" % "1.4.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Xfuture",
  "-Xcheckinit"
)

publishMavenStyle := true

pomIncludeRepository := { _ => false }

publishArtifact in Test := false

publishTo := {
  val nexus = "http://somewhere/nexus/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("Nexus Snapshots" at nexus + "content/repositories/snapshots/")    
  else
    Some("Nexus Releases" at nexus + "content/repositories/releases")
}

credentials += Credentials("Sonatype Nexus Repository Manager", "somewhere", "user", "password")
