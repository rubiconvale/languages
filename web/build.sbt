name := "main-web"

version := "1.0-SNAPSHOT"


lazy val languageServices=file("../services")

scalaVersion := "2.10.4"

resolvers ++= Seq(
 "ArchivaRepoSnapshots" at "http://archiva.picsauditing.com/repository/snapshots/"
 )

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.apache.commons" % "commons-lang3" % "3.3.2",
  "org.slf4j" % "slf4j-api" % "1.7.7",
  "org.apache.httpcomponents" % "httpclient" % "4.3.5",
  "org.springframework" % "spring-web" % "4.1.1.RELEASE",
  "org.springframework" % "spring-context" % "4.1.1.RELEASE",
  "org.springframework" % "spring-core" % "4.1.1.RELEASE",
  "org.springframework" % "spring-beans" % "4.1.1.RELEASE",
  "org.springframework" % "spring-orm" % "4.1.1.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.1.1.RELEASE",
  "org.springframework" % "spring-tx" % "4.1.1.RELEASE",
  "org.springframework" % "spring-expression" % "4.1.1.RELEASE",
  "org.springframework" % "spring-aop" % "4.1.1.RELEASE",
  "org.springframework" % "spring-test" % "4.1.1.RELEASE" % "test",
  "com.wordnik" %% "swagger-play2" % "1.3.12",
  "com.picsauditing.common" %% "core-commons" % "0.0.5-SNAPSHOT"
)


lazy val root = (project in file(".")).enablePlugins(PlayJava).dependsOn(languageServices)