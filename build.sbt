import spray.revolver.RevolverPlugin._

seq(Revolver.settings: _*)

releaseSettings

scalariformSettings

organization := "co.s4n"

name := "capacitaciones"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
  "com.chuusai"                 %%  "shapeless"                 % "2.1.0" withSources() withJavadoc(),
  "org.scalaz"                  %%  "scalaz-core"               % "7.1.1" withSources() withJavadoc(),
  "com.typesafe.scala-logging"  %%  "scala-logging"             % "3.1.0" withSources() withJavadoc(),
  "net.ceedubs"                 %%  "ficus"                     % "1.1.2" withSources() withJavadoc(),
  "org.scalatest"               %   "scalatest_2.11"            % "2.2.4" % "test"
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
