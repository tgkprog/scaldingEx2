package academic

object SamplesArr extends App {
  val a = Array("p1", "p2", "p3")
  val p = "p2"
  println("start str a :" + a.mkString)
  testArrStr(a, "p1")
  testArrStr(a, "p2")
  testArrStr(a, "p3")
  testArrStr(a, "p4")
  println("start arr a :" + a.mkString)
  testArrStr(a, "p1")
  testArrStr(a, "p2")
  testArrStr(a, "p3")
  testArrStr(a, "p4")

  def procArr(ar: Array[String], p: String): Array[String] = {
    val b = for (e <- a if !e.equals(p)) yield e
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
}