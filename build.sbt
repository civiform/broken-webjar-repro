name := """play-java-hello-world-tutorial"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.8"

libraryDependencies += guice

libraryDependencies ++= Seq(
  "org.webjars.npm" % "azure__storage-blob" % "10.5.0"
)

Global / onChangedBuildSource := ReloadOnSourceChanges
