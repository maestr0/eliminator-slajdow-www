name := """eliminator-slajdow-www"""

version := "1.0-SNAPSHOT"

play.Project.playScalaSettings

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "2.1.0-M2",
  "com.jolbox" % "bonecp" % "0.8.0.RELEASE",
  "org.mockito" % "mockito-all" % "1.9.5" % "test",
  "com.h2database" % "h2" % "1.3.175" % "test",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.scalatest" %% "scalatest" % "2.1.2" % "test",
  "ch.qos.logback" % "logback-classic" % "1.0.6",
  "com.github.sebrichards" %% "postmark-scala" % "1.1.1"
)
