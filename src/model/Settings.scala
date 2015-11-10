package model

import model.{Eds, Diod}

/**
 * Created by neikila on 02.11.15.
 */
class Settings {
  val c4: Double = math.pow(10, -6)
  val l3: Double = 0.001
  val r4: Double = 1000.0
  val diod: Diod = new Diod(math.pow(10, 6), 2 * math.pow(10, -12), 20, math.pow(10, -12), 0.026)
  val eds: Eds = new Eds(2 * math.Pi * 10000, 10)

  val deadline = math.pow(10, -3)

  val startDt = math.pow(10, -8) * 5
}
