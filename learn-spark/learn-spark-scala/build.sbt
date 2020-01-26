
name := "learn-spark"

version := "1.0"

scalaVersion := "2.11.12"

val sparkVersion = "2.4.0"
val pdfboxVersion = "2.0.13"
//val tikaVersion = "1.20"

resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  // "org.apache.tika" % "tika-core" % tikaVersion,
  // "org.apache.tika" % "tika-parsers" % tikaVersion,
  "org.apache.pdfbox" % "pdfbox" % pdfboxVersion,
  "mysql" % "mysql-connector-java" % "5.1.6"
)
