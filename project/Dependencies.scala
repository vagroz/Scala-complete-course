import sbt._

object Dependencies {
  val importantResolvers = Seq(
    Resolver.jcenterRepo,
    Resolver.url("jCenter", url("http://jcenter.bintray.com/")),
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )

  val akka = Seq("com.typesafe.akka" %% "akka-actor" % "2.5.3",
    "com.typesafe.akka" %% "akka-testkit" % "2.5.3" % Test,
    "com.typesafe.akka" %% "akka-remote" % "2.5.3",
    "com.typesafe.akka" %% "akka-slf4j" % "2.5.3"
  )

  val scala = Seq(
    "org.scala-lang" % "scala-library" % "2.12.2",
    "org.scala-lang" % "scala-reflect" % "2.12.2",
    "org.scala-lang.modules" %% "scala-async" % "0.9.7",
    "org.scala-lang" % "scala-library" % "2.12.2",
    "org.scala-lang" % "scala-reflect" % "2.12.2"
  )

  val testDependencies = Seq(
    "org.typelevel" %% "cats-core" % "1.0.0-MF",
    "org.xerial" % "sqlite-jdbc" % "3.7.2",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
    "de.sciss" %% "coroutines-common" % "0.1.0",
    "com.storm-enroute" %% "scalameter" % "0.9"
  )

  val  softwaremill = Seq(
    "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided",
    "com.softwaremill.macwire" %% "macrosakka" % "2.3.0" % "provided",
    "com.softwaremill.macwire" %% "util" % "2.3.0",
    "com.softwaremill.macwire" %% "proxy" % "2.3.0"
  )

  val scalaOpts = Seq(
    "-encoding", "utf8",
    "-feature",
    "-unchecked",
    "-deprecation",
    "-target:jvm-1.8",
    "-Ymacro-debug-lite",
    "-language:experimental.macros"
  )

}
