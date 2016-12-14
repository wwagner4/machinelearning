lazy val commonSettings = Seq(
  organization := "net.entelijab",
  version := "0.1.0",
  scalaVersion := "2.11.8",
  libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.1" % "test")

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "scala-machinelearning")