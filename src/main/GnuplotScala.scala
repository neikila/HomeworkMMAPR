package main

import java.io.PrintWriter

import model.XVector
import model.Settings

import scala.collection.mutable.ArrayBuffer

/**
 * Created by neikila on 05.11.15.
 */
class GnuplotScala (val results: Array[(Double, XVector, Double)], val settings: Settings){
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
    printToFile(Uc4, (x: XVector) => x.Uc4() * 10 / 0.3)
//    printMinMaxValues
    createGnuplotScript("I_result.script", Array(Il3, Ie), "I, A", "Time, s")
    createGnuplotScript("U_result.script", Array(Ur4, Uc4, Ue), "U, V", "Time, s")
  }

  def printToFile(fileName: String, f: (XVector) => Double): Unit = {
    val out = new PrintWriter(directory + fileName)
    var max = f(results(0)._2)
    var min = f(results(0)._2)
    for ((time, vector, _) <- results) {
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

  def createGnuplotScript(fileName: String, graph: Array[String], YTitle: String, XTitle: String): Unit = {
    val (a, b) = maxMinValues.filterKeys((a: String) => graph.contains(a)).values.unzip
    val max = a.max
    val min = b.min

    val out = new PrintWriter(directory + fileName)
    var scriptCode = "set terminal x11 size 1360, 700\n" +
      "set xlabel '" + XTitle + "'\n" +
      "set ylabel '" + YTitle + "'\n" +
      "set xrange [" + 0 + ":" + settings.deadline + "]\n" +
      "set yrange [" + min + ":" + max + "]\n" +
      "set grid\n" +
      "plot "
    for (i <- 0 until (graph.size - 1)) {
      scriptCode += "'" + graph(i) + "' using 1:2 w l lw 2 title '" +
        graph(i) + "',\\\n"
    }
    scriptCode += "'" + graph.last + "' using 1:2 w l lw 2 title '" + graph.last + "'\n" +
      "pause -1";
    out.print(scriptCode)
    out.close()
  }
}
