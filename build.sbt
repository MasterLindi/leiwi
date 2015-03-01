name := "leiwi"

scalaVersion := "2.11.5"

lazy val commonSettings = Seq(
  organization := "at.fhtw",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.4"
)

lazy val leiwi = (project in file("."))
  .settings(commonSettings: _*)
  .enablePlugins(PlayScala)
  .dependsOn(wfsconnector)
  .aggregate(wfsconnector)

lazy val addressconnector = project

lazy val dal = project

lazy val wfsconnector = (project in file("wfsconnector"))
  .settings(commonSettings: _*)

lazy val webapiconnector = project

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)



