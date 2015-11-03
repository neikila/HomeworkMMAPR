package model

/**
 * Created by neikila on 02.11.15.
 */
class Eds (val omega: Double, val Emax: Double){
  def E(t: Double) = Emax * math.sin(omega * t)
}
