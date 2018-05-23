import _root_.sbt.Keys._
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
    libraryDependencies ++= softwaremill
  )
  .settings(assemblySettings: _*)
  .configs(oop)
  .settings(
    inConfig(oop)(Defaults.testTasks),
    testOptions in oop := Seq(
      Tests.Filter(_.startsWith("lectures.oop."))
    )
  )


val assemblySettings = Seq (
  test in assembly := {},
  mainClass in assembly := Some("lectures.oop.TreeTest"),
  assemblyOutputPath in assembly := file(s"target/scala-complete-course-${version.value}.jar")
)

lazy val oop = config("oop") extend(Test)

