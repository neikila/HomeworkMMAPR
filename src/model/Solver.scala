package model

import java.{lang, util}

import main.Gaus

import scala.collection.mutable.ArrayBuffer

/**
 * Created by neikila on 05.11.15.
 */
class Solver {
  val settings = new Settings
  val model = new MathModel(settings)

  protected var resultSolution = ArrayBuffer[(Double, XVector)]((0.0, new XVector))
  def result = resultSolution

  protected var time = 0.0
  protected var dt = settings.startDt

  def solve(): Unit = {
    var i = 0
    while (time < settings.deadline) {
      resultSolution += ((time, new Step(resultSolution.last._2).calculate))
      time += dt
      i += 1
      if (i % 100000 == 0) {
        println(i)
        println(time)
      }
    }
  }

  class Step (private val previousStep: XVector){
    private var iterationNum = 0
    private var iterationApproximation = new XVector(new util.ArrayList(previousStep.list))

    def calculate = {
      var B: java.util.List[java.lang.Double] = new util.ArrayList[lang.Double]()
      // iterations
      do {
        val A = model.getAMatrix(dt, iterationApproximation.getDeltaU)
        B = model.getBMatrix(iterationApproximation, previousStep, time, dt)

        Gaus.solve(A, B)
        iterationApproximation += B
      } while (!checkIfEnd(B))
      iterationApproximation
    }

    private def checkIfEnd(delta: java.util.List[java.lang.Double]): Boolean = {
      var result = true
      for (i <- 0 until delta.size())
        if (math.abs(delta.get(i)) >= 0.001 && result)
          result = false
      if (!result) {
        if (iterationNum < 7) {
          iterationNum += 1
        } else {
          dt /= 2
          println(dt)
          iterationApproximation = new XVector(new util.ArrayList(previousStep.list))
          iterationNum = 0
        }
      }
      result
    }
  }
}