package model

import java.util

/**
 * Created by neikila on 02.11.15.
 */
class XVector (var list: java.util.List[java.lang.Double]){

  def this() = {
    this({
      var temp1 = new util.ArrayList[java.lang.Double]()
      for (i <- 0 until 19) temp1.add(0.0)
      temp1
    })
  }

  def dUc4dt()  =  list.get(0)
  def dIl3dt()  =  list.get(1)
  def dUcddt()  =  list.get(2)
  def Ul3()     =  list.get(3)
  def Ury()     =  list.get(4)
  def Uid()     =  list.get(5)
  def Urd()     =  list.get(6)
  def Ie()      =  list.get(7)
  def Ic4()     =  list.get(8)
  def Icd()     =  list.get(9)
  def Ir4()     =  list.get(10)
  def Il3()     =  list.get(11)
  def Iry()     =  list.get(12)
  def Iid()     =  list.get(13)
  def Ird()     =  list.get(14)
  def Ue()      =  list.get(15)
  def Uc4()     =  list.get(16)
  def Ucd()     =  list.get(17)
  def Ur4()     =  list.get(18)

  def getDeltaU = Ury + Urd

  def get(i: Int) = {
    list.get(i)
  }

  def + (vector: XVector) = {
    val addList = vector.list
    new XVector({
      var temp = new util.ArrayList[java.lang.Double]()
      for (i <- 0 until list.size())
        temp.add(list.get(i) + addList.get(i))
      temp
    })
  }

  def += (vector: java.util.List[java.lang.Double]) {
    for (i <- 0 until list.size())
      list.set(i ,list.get(i) + vector.get(i))
  }

  def += (vector: XVector) {
    this += vector.list
  }
}
