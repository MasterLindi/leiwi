import sbt.Keys._

name := "leiwi"

scalaVersion := "2.11.5"


val buildResolvers = resolvers ++= Seq(
  "Open Source Geospatial Foundation Repository" at "http://download.osgeo.org/webdav/geotools"
)

lazy val junitDep = "junit" % "junit" % "4.11" % Test

lazy val guavaDep = "com.google.guava" % "guava" % "16.0.1"

lazy val javaxMediaDep = "javax.media" % "jai_core" % "1.1.3" from "http://download.osgeo.org/webdav/geotools/javax/media/jai_core/1.1.3/jai_core-1.1.3.jar"

lazy val geoToolsWfsDep = "org.geotools" % "gt-wfs" % "13-RC1" exclude("javax.media", "jai_core")

lazy val geoToolsHsqlDep =  "org.geotools" % "gt-epsg-hsql" % "13-RC1"

//lazy val geoToolsPostgresqlDep =  "org.geotools" % "gt-epsg-postgresql" % "13-RC1"

lazy val postgis =  "org.postgis" % "postgis-jdbc" % "1.3.3"

lazy val postgreV4 = "postgresql" % "postgresql" % "9.1-901.jdbc4"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  json,
  postgreV4,
  javaxMediaDep,
  geoToolsWfsDep,
  geoToolsHsqlDep,
//  postgis,
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)

lazy val commonSettings = Seq(
  organization := "at.fhtw",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.4"
)

javacOptions in wfsconnector ++= Seq("-encoding", "UTF-8")

lazy val leiwi = (project in file("."))
  .settings(commonSettings: _*)
  .enablePlugins(PlayScala)
  .dependsOn(wfsconnector)
  .dependsOn(addressconnector)
  .aggregate(wfsconnector, addressconnector)

lazy val addressconnector = (project in file("addressconnector"))
  .settings(
    Seq(
      projectDependencies ++= Seq(
        junitDep
      )
    ):_*)
  .settings(commonSettings: _*)

lazy val dal = (project in file("dal"))
  .settings(
    Seq(
      projectDependencies ++= Seq(
        junitDep
      )
    ):_*)
  .settings(commonSettings: _*)

lazy val wfsconnector = (project in file("wfsconnector"))
  .settings(buildResolvers: _*)
  .settings(
    Seq(
      projectDependencies ++= Seq(
        "com.novocode" % "junit-interface" % "0.11" % Test,
        junitDep,
        guavaDep,
        javaxMediaDep,
        geoToolsWfsDep,
        geoToolsHsqlDep,
        postgreV4,
        postgis
      )
    ):_*)
    .settings(commonSettings: _*)

lazy val webapiconnector = project




