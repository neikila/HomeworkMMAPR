package main

import scala.collection.mutable.ArrayBuffer

/**
 * Created by neikila on 03.11.15.
 */
object GausScala {
  def solve(A: ArrayBuffer[ArrayBuffer[Double]], B: ArrayBuffer[Double]) = {
    for (i <- 0 until A.length) {
      if (A(i)(i) == 0) {
        var switched = false
        for (k <- (i + 1) until A.length if A(k)(i) != 0 && !switched) {
          switched = true
          val temp = A(k)
          A(k) = A(i)
          A(i) = temp
          val temp2 = B(i)
          B(i) = B(k)
          B(k) = temp2
        }
      }
      val diagonal = A(i)(i)

      A(i) = for (el <- A(i)) yield el / diagonal
      B(i) /= diagonal

      for (k <- (i + 1) until A.length) {
        val first = A(k)(i)
        A(k) = for ((element, elementUp) <- A(k) zip A(i)) yield {element - elementUp * first}
        B(k) = B(k) - B(i) * first
      }
    }

    for (k <- (0 until A.length).reverse; i <- 0 to (k - 1)) { B(i) -= B(k) * A(i)(k); A(i)(k) = 0 }
  }

  def swap[A,B](a: A, b: B): (B, A) = (b, a)
}
