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

    for ((time, vector) <- results) {
      out.println(time + " " + f(vector))
    }
    out.close()
  }
}
