package academic

object GroupExceptSelf {
  val source = List((1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (2, 4), (2, 5))
  val s2= source.foldLeft(Map[Int, List[Int]]())((m, e) =>
    if (e._1 == e._2) m else m + (e._1 -> (e._2 :: m.getOrElse(e._1, List()))))
   println(s2)
}