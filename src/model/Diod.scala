package model

/**
 * Created by neikila on 02.11.15.
 */
class Diod (val Ry: Double, val C: Double, val Rr: Double, val It: Double, val mft: Double){
  def getMultiplier(deltaU:Double) =
    It * math.exp(deltaU / mft) / mft

  def getI(deltaU: Double) = It * (math.exp(deltaU / mft) - 1)
}
