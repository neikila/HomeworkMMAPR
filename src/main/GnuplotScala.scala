package main

import java.io.PrintWriter

/**
 * Created by neikila on 05.11.15.
 */
class GnuplotScala {
  val results = ""
  val directory = "/out/"

  def printToFile(fileName: String): Unit = {
    val out = new PrintWriter(directory + fileName)

  }
}
