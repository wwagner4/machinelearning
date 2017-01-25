lazy val commonSettings = Seq (
  organization := "net.entelijab",
  version := "0.1.0",
  scalaVersion := "2.12.1",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  libraryDependencies += "org.scalanlp" %% "breeze" % "0.13",
  libraryDependencies += "org.scalanlp" %% "breeze-natives" % "0.13")

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "scala-machinelearning")