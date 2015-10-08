package academic

import scala.collection.mutable.ArrayBuffer

object SamplesArr extends App {
  val a = Array("p1", "p2", "p3", "p6", "p7", "p9", "p8")
  val a1 = Array("p1", "p3", "p6")
  val p = "p2"
  //  println("start str a :" + a.mkString)
  //  testArrStr(a, "p1")
  //  testArrStr(a, "p2")
  //  testArrStr(a, "p3")
  //  testArrStr(a, "p4")
  println("start arr a :" + a.mkString)
  testArrSz(a, "p1", -1)
  testArrSz(a, "p2", 2)
  testArrSz(a, "p3", 5)
  testArrSz(a, "p8", 5)
  testArrSz(a, "p1", 5)
  testArrSz(a, "p4", 5)
  testArrSz(a1, "p4", 5)
  testArrSz(a1, "p3", 5)
  testArrSz(a1, "p1", 5)
  testArrSz(a1, "p6", 5)
  testArrSz(a1, "p4", 2)

  def procArr(ar: Array[String], p: String): Array[String] = {
    val b = for (e <- ar if !e.equals(p)) yield e
    b
  }

  def testArr(ar: Array[String], p: String): Unit = {
    val t = procArr(ar, p)
    println(p + " : " + t.mkString(","))
  }

  def procArrToStr(ar: Array[String], p: String): String = {
    val sb: StringBuilder = new StringBuilder
    for (e <- a if !e.equals(p)) {
      sb.append(e).append(",")
    }
    if (sb.length > 0) sb.deleteCharAt(sb.length - 1)
    sb.toString
  }

  def testArrStr(ar: Array[String], p: String): Unit = {
    val t = procArrToStr(ar, p)
    println(p + " : " + t)
  }

  def procArrSz(ar: Array[String], p: String, max: Int = -1) = {
    var i = 0;
    var work: Boolean = true
    val bu: ArrayBuffer[String] = ArrayBuffer()
    do {
      if (!ar(i).equals(p)) {
        bu.+=(ar(i))
        if (max == bu.length) work = false
      }
      i += 1
      if (i == ar.length) work = false
    } while (work)
    bu
  }

  def testArrSz(ar: Array[String], p: String, m: Int): Unit = {
    val t = procArrSz(ar, p, m)
    println("in : " + ar.mkString(",") + ", p :" + p + ", max :" + m + " out : " + t.mkString(","))
  }
}