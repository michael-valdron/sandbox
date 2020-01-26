package example.spark.documents.dp

import java.io.File
import java.io.IOException

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.feature.{HashingTF, IDF, IDFModel}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD
import org.apache.spark.input.PortableDataStream
import org.apache.spark.SparkException

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper

object PDFDocument {
  def parsePDF(f: (String, PortableDataStream)): Seq[String] = {
    val file: File = new File(f._1.drop(5))
    val doc = PDDocument.load(file)
    val docStripper = new PDFTextStripper
    docStripper.setAddMoreFormatting(true)

    return docStripper
      .getText(doc)
      .replaceAll("!\\w+", "")
      .split("\\s+")
      .toSeq
  }
}

class DocumentProcessing(inputPath: String, outPath: String) {
  private val inputFilesPath: String = inputPath
  private val outputFilePath: String = outPath
  private val conf: SparkConf = new SparkConf().setAppName("DocumentProcessor").setMaster("local[*]")
  private val sc: SparkContext = new SparkContext(conf)
  private var dict: Seq[Seq[String]] = Seq()
  private var docs: RDD[Seq[String]] = null

  def loadDocuments(): Unit = {
    val files = sc.binaryFiles(inputFilesPath)
    docs = files.map(d => PDFDocument.parsePDF(d))
  }

  def docCount(): Unit = {
    val counts = docs.map(d => d.length)
    counts.saveAsTextFile(outputFilePath)
  }

  def tfidf(min: Integer = 1): Unit = {
    val hashingTF: HashingTF = new HashingTF()
    val tf: RDD[Vector] = hashingTF.transform(docs)
    var idf: IDFModel = null
    var out: RDD[Vector] = null

    tf.cache
    idf = new IDF(min).fit(tf)
    out = idf.transform(tf)
    out.saveAsTextFile(outputFilePath)
  }

  def close(): Unit = sc.stop()
}
