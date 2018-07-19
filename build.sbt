name := "cellular"

version := "0.1"

scalaVersion := "2.12.5"

mainClass in Compile := Some("Main")

libraryDependencies += "net.liftweb" %% "lift-webkit" % "3.1.1"
libraryDependencies += "org.xerial.sbt" % "sbt-pack" % "0.11"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.0"

exportJars := true

enablePlugins(PackPlugin)

packMain := Map("cellular" -> "Main")