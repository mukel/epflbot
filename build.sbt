name := "EPFLBot"
version := "0.1.0"

scalaVersion := "2.11.8"
scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint:_"
)

resolvers += "jitpack" at "https://jitpack.io"
resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "com.github.mukel" %% "telegrambot4s" % "v2.0.1" exclude ("org.slf4j", "slf4j-log4j12") exclude ("ch.qos.logback", "logback-classic"),
  "net.ruippeixotog" %% "scala-scraper" % "1.1.0",
  "com.github.nscala-time" %% "nscala-time" % "2.14.0",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % "5.0.4",
  "com.sksamuel.elastic4s" %% "elastic4s-circe" % "5.0.4",
  "com.vividsolutions" % "jts" % "1.13",
  "org.locationtech.spatial4j" % "spatial4j" % "0.6",
  "org.apache.logging.log4j" % "log4j-core" % "2.6.2",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.6.2"
)

scalafmtConfig := Some(file(".scalafmt.conf"))

test in assembly := {}
target in assembly := file("dock")
assemblyJarName in assembly := "epflbot.jar"
mainClass in assembly := Some("ch.epfl.telegram.InlineEpflBot")
assemblyMergeStrategy in assembly := {
  case "META-INF/io.netty.versions.properties" => MergeStrategy.first
  case x if x.startsWith("org/apache/logging/log4j") => MergeStrategy.first
  case x if x.startsWith("io/netty") => MergeStrategy.first
  case x                                       => (assemblyMergeStrategy in assembly).value(x)
}