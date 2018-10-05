import _root_.sbt.Keys._

name := "scala-course"

version := "1.0"

scalaVersion := "2.12.6"

scalacOptions := Seq(
  "-encoding", "utf8",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-target:jvm-1.8",
  "-Ymacro-debug-lite",
  "-language:experimental.macros")

resolvers ++= Seq(
  Resolver.jcenterRepo,
  Resolver.url("jCenter", url("http://jcenter.bintray.com/")),
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"))

libraryDependencies += "org.scala-lang.modules" %% "scala-async" % "0.9.7"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")

libraryDependencies += "org.scala-lang" % "scala-library" % "2.12.6"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.12.6"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0"

libraryDependencies += "org.typelevel" %% "cats-free" % "1.1.0"

libraryDependencies += "org.typelevel" %% "cats-laws" % "1.1.0"

libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3"

libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.7.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "de.sciss" %% "coroutines-common" % "0.1.0"

libraryDependencies += "org.typelevel" %% "cats-effect" % "1.0.0-RC2"

libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-actor" % "2.5.3",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.3" % Test,
  "com.typesafe.akka" %% "akka-remote" % "2.5.3",
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.3"
)
import Dependencies._


lazy val `scala-complete-course` = (project in file("."))
  .settings(
    name := "Scala-complete-course",
    version := "1.0",
    scalaVersion := "2.12.2",
    scalacOptions := scalaOpts,
    resolvers ++= importantResolvers,
    parallelExecution in ThisBuild := false
  )
  .settings(
    libraryDependencies ++= scala,
    libraryDependencies ++= testDependencies,
    libraryDependencies ++= akka,
    libraryDependencies ++= softwaremill,
    libraryDependencies += "org.scaldi" %% "scaldi" % "0.5.8"
  )
  .settings(assemblySettings: _*)


val assemblySettings = Seq (
  test in assembly := {},
  mainClass in assembly := Some("lectures.oop.TreeTest"),
  assemblyOutputPath in assembly := file(s"target/scala-complete-course-${version.value}.jar")
)

