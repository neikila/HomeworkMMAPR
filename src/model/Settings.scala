package model

import model.{Eds, Diod}

/**
 * Created by neikila on 02.11.15.
 */
class Settings {
  val c4: Double = 1
  val l3: Double = 1
  val r4: Double = 1
  val diod: Diod = new Diod(0, 0, 0, 0, 0)
  val eds: Eds = new Eds(1, 1)
}
