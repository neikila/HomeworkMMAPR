package model

import java.util

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
  protected var dt = math.pow(10, -7) * 5

  def solve(): Unit = {
    while (time < settings.deadline) {
      time += dt
      resultSolution += ((time, new Step(resultSolution.last._2).calculate))
    }
  }

  class Step (private val previousStep: XVector){
    private var iterationNum = 0
    private var iterationApproximation = new XVector(new util.ArrayList(previousStep.list))

    def calculate = {
      var B: java.util.List[java.lang.Double] = new util.ArrayList[java.lang.Double]()
      // iterations
      do {
        val A = model.getAMatrix(dt, iterationApproximation.getDeltaU)
        B = model.getBMatrix(iterationApproximation, previousStep, time)

        Gaus.solve(A, B)
        iterationApproximation += B
      } while (!checkIfEnd(B))
      iterationApproximation
    }

    private def checkIfEnd(delta: java.util.List[java.lang.Double]): Boolean = {
      var result = true
      for (i <- 0 until delta.size() if result)
        if (delta.get(i) >= 0.001)
          result = false
      if (!result) {
        if (iterationNum < 7) {
          iterationNum += 1
        } else {
          dt /= 2
          iterationApproximation = new XVector(new util.ArrayList(previousStep.list))
          iterationNum = 0
        }
      }
      result
    }
  }
}