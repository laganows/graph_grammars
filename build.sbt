name := "graph_grammars"

version := "0.1"

autoScalaLibrary := false

cancelable in Global := true

lazy val Version = new {
  lazy val GraphStream = "1.3"
  lazy val Guava = "21.0"
  lazy val JUnit = "4.12"
  lazy val ScalaTest = "3.0.1"
  lazy val Lombok = "1.16.16"
  lazy val Log4j = "1.2.14"
}

libraryDependencies ++= Seq(
  "org.graphstream" % "gs-core" % Version.GraphStream,
  "org.graphstream" % "gs-ui" % Version.GraphStream,
  "org.graphstream" % "gs-algo" % Version.GraphStream,
  "com.google.guava" % "guava" % Version.Guava,
  "org.projectlombok" % "lombok" % Version.Lombok,
  "log4j" % "log4j" % Version.Log4j,
  "junit" % "junit" % Version.JUnit % Test,
  "org.scalatest" %% "scalatest" % Version.ScalaTest % Test,
  "org.hamcrest" % "hamcrest-library" % "1.3"
)