name := """eliminator-slajdow-www"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "2.1.0-M2",
  "postgresql" % "postgresql" % "9.3-1101.jdbc4",
  "com.jolbox" % "bonecp" % "0.8.0.RELEASE",
  "org.mockito" % "mockito-all" % "1.9.5" % "test",
  "com.h2database" % "h2" % "1.3.175" % "test",
  "ch.qos.logback" % "logback-classic" % "1.0.6"
)
