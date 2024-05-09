val scala3Version = "3.4.1"

lazy val root = project
  .in(file("."))
  .enablePlugins(ScoverageSbtPlugin)
  .settings(
    name := "Pokeymon",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test",
    coverageEnabled := true
  )
