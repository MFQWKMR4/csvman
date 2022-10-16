import Dependencies._

ThisBuild / scalaVersion     := "2.12.13"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "core"

lazy val root = (project in file("."))
  .settings(
    name := "csvman",
    libraryDependencies ++= Seq(
      scalaTest % Test
      )
  )
  .enablePlugins(ScalaNativePlugin)
