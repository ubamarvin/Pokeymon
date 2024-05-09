val scala3Version = "3.4.1"

lazy val root = project
  .in(file("."))
  .enablePlugins(ScoverageSbtPlugin)
  .settings(
    name := "Pokeymon",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test"
  )

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.11")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.3.1")
