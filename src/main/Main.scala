package main

import java.{lang, util}

import model._
import collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
 * Created by neikila on 02.11.15.
 */
object Main {
  def main(args: Array[String]) {
    println("Hello, world")
    val temp1 = ArrayBuffer[Double] (1.0, 2.0, 3.0)
    val temp2 = ArrayBuffer(4.0, 4.0, 2.0)
    val A = ArrayBuffer(temp1, ArrayBuffer(1.0, 2.0, 0.0), temp2)
    val B = ArrayBuffer(6.0, 3.0, 10.0)
    GausScala.solve(A, B)
    println(A + " " + B)


//    var temp = new XVector

//    val settings = new Settings
//    val mathModel = new MathModel(settings)
//    var previous = new XVector


//    for (i <- 1 to 20) {
//      var approximateBefore = previous
//    }
  }
}
