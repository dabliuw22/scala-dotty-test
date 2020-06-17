import sbt._

object Dependencies {
  def cats(artifact: String): ModuleID = "org.typelevel" %% artifact % "2.1.1"
  lazy val scalaTest: ModuleID = "org.scalatest" %% "scalatest" % "3.0.8" % Test
  lazy val jUnit: ModuleID = "com.novocode" % "junit-interface" % "0.11" % Test
  lazy val dottyVersion = "0.24.0-RC1"
}
