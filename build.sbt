import scala.io.Source

androidDefaults

name := "AkkaDroid"

version := "0.1"

versionCode := 0

scalaVersion := "2.10.3"

platformName := "android-15"

libraryDependencies += "com.typesafe.akka"             %% "akka-actor"              % "2.3.0"

proguardOptions += Source.fromFile("project/proguard.cfg").mkString
