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

  protected var time = 0.0
  protected var dt = settings.startDt
  protected var lastdt = dt

  protected var resultSolution = ArrayBuffer[(Double, XVector, Double)]((0.0, new XVector, dt))
  def result = resultSolution

  def solve(): Unit = {
    var i = 0
    while (time < settings.deadline) {
      resultSolution += ((time, new Step(resultSolution.last._2).calculate, dt))
      time += dt
      lastdt = dt
      i += 1
      if (i % 100000 == 0) {
        println("i = " + i)
        println("Time = " + time)
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
      for (i <- 0 until delta.size() if result)
        if (math.abs(delta.get(i)) >= 0.001)
          result = false
      if (!result) {
        if (iterationNum > 6) {
          dt /= 2
          println(dt)
          iterationApproximation = new XVector(new util.ArrayList(previousStep.list))
          iterationNum = 0
        } else {
          iterationNum += 1
        }
      } else {
        if (resultSolution.size > 2)
          result = analyzeDeviation(new XVector(delta))
      }
      result
    }

    private def analyzeDeviation(xVector: XVector): Boolean = {
      val fprev = resultSolution(resultSolution.length - 2)._2.Ue()
      val flast = resultSolution(resultSolution.length - 1)._2.Ue()
      val fcur = settings.eds.E(time + dt)
      val epsilon = math.abs((dt / (dt + lastdt)) * (fcur - flast - (dt / lastdt) * (flast - fprev)))
      if (epsilon > settings.highLevel) {
        dt /= 2
        false
      } else {
        if (epsilon < settings.lowLevel && dt < 0.0000001) {
          dt *= 2
        }
        true
      }
    }
  }
}