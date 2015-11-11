package main

import java.io.PrintWriter

import model.XVector

import scala.collection.mutable.ArrayBuffer

/**
 * Created by neikila on 05.11.15.
 */
class GnuplotScala (val results: Array[(Double, XVector)]){
  val directory = "out/"
  val maxMinValues = scala.collection.mutable.Map[String, (Double, Double)]()
  val Il3 = "Il3"
  val Ie = "Ie"
  val Ue = "Ue"
  val Ur4 = "Ur4"
  val Uc4 = "Uc4"

  def printAll(): Unit = {
    printToFile(Il3, (x: XVector) => x.Il3())
    printToFile(Ie, (x: XVector) => x.Ie())
    printToFile(Ue, (x: XVector) => x.Ue())
    printToFile(Ur4, (x: XVector) => x.Ur4())
    printToFile(Uc4, (x: XVector) => x.Uc4())
    printMinMaxValues
    createGnuplotScript("asd", Array(Il3, Ie))
  }

  def printToFile(fileName: String, f: (XVector) => Double): Unit = {
    val out = new PrintWriter(directory + fileName)
    var max = f(results(0)._2)
    var min = f(results(0)._2)
    for ((time, vector) <- results) {
      out.println(time + " " + f(vector))
      if (f(vector) > max) {
        max = f(vector)
      }
      if (f(vector) < min) {
        min = f(vector)
      }
    }
    maxMinValues += (fileName -> (max, min))
    out.close()
  }

  def printMinMaxValues(): Unit = {
    for ((filename, (max, min)) <- maxMinValues) {
      println(filename + ": max: " + max + " min: " + min)
    }
  }

  def createGnuplotScript(filename: String, grapth: Array[String]): Unit = {
    val (a, b) = maxMinValues.filterKeys((a: String) => grapth.contains(a)).values.unzip
    println(a)
    println(b)
  }
}
