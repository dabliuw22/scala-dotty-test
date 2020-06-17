import Dependencies._

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-dotty-test",
    version := "0.1.0",
    scalaVersion := dottyVersion,
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-unchecked",
      "-language:implicitConversions"
    ),
    libraryDependencies ++= Seq(
      cats("cats-macros").withDottyCompat(dottyVersion),
      cats("cats-kernel").withDottyCompat(dottyVersion),
      cats("cats-core").withDottyCompat(dottyVersion),
      cats("cats-effect").withDottyCompat(dottyVersion),
      scalaTest.withDottyCompat(dottyVersion),
      jUnit
    )
  )
