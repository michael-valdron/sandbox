package example.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import documents.dp.DocumentProcessing

object SparkSandbox {
  def main(args: Array[String]): Unit = {
    val d: DocumentProcessing = new DocumentProcessing("./data/docs/*", "./data/output")
    d.loadDocuments()
    d.tfidf()
    d.close()
    return
  }
}
