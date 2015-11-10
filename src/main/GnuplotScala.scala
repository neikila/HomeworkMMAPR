package main

import java.io.PrintWriter

import model.XVector

/**
 * Created by neikila on 05.11.15.
 */
class GnuplotScala (val results: Array[(Double, XVector)]){
  val directory = "out/"

  def printAll(): Unit = {
    printToFile("Il3", (x: XVector) => x.Il3())
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
    println("Max = " + max)
    println("Min = " + min)
    out.close()
  }
}
