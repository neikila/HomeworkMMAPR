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

  private var resultSolution = ArrayBuffer[XVector](new XVector)

  private var time = 0.0
  private var dt = 0.001

  def solve(): Unit = {
    while (time < settings.deadline) {
      resultSolution += new Step(resultSolution head).calculate
      time += dt
    }
  }

  class Step (private var previousStep: XVector){
    private var iterationNum = 0
    private var iterationApproximation = previousStep

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
      for (i <- 0 to delta.size(); result)
        if (delta.get(i) >= 0.001)
          result = false
      if (!result) {
        if (iterationNum < 7) {
          iterationNum += 1
        } else {
          dt /= 2
          iterationApproximation = previousStep
          iterationNum = 0
        }
      }
      result
    }
  }
}