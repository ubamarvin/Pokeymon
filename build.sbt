val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Pokeymon",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
    libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32",
    libraryDependencies += "com.google.inject" % "guice" % "7.0.0",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.3.0",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC5",
    libraryDependencies ++= {
      // Determine OS version of JavaFX binaries
      lazy val osName = System.getProperty("os.name") match {
        case n if n.startsWith("Linux")   => "linux"
        case n if n.startsWith("Mac")     => "mac-aarch64"
        case n if n.startsWith("Windows") => "win"
        case _                            => throw new Exception("Unknown platform!")
      }
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "22.0.1" classifier osName)
    },
    //fork := true,
    javaOptions ++= Seq(
      s"--module-path", "/Users/kadirhasanovic/javafx-sdk-22.0.1/lib",
      "--add-modules", "javafx.controls,javafx.fxml,javafx.web",
      "--add-exports", "javafx.graphics/com.sun.javafx.application=ALL-UNNAMED"
    ),
    coverageEnabled := true
  )
