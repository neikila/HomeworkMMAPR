package main

import model.Diod
import math._
import model._

/**
 * Created by neikila on 02.11.15.
 */
object Main {
  def main(args: Array[String]) {
    println("Hello, world")
    for (i <- 1 to 10)
      print(i + " ")
    println(2 to 10)
    println(new Diod(1,1,1,1,1).getMultiplier(10))
  }
}
