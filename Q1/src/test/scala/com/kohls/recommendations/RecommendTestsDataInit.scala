package com.kohls.recommendations

object RecommendTestsDataInit {
  def getObjs1() = {
    val catsData = List(
      ("run_c2a_badminton", "p8,p10"), ("walk_c2a_office", "p11"), ("jog_t2b_summer", "p12,p18,p19,p23,p26,p28,p30"), ("walk_elegant_office", "p13"), ("walk_t2c_saunter", "p14,p15,p16,p17,p22,p31"), ("run_t2a_tennis", "p20,p24,p29,p32"), ("jog_c2a_summer", "p9,p6,p12,p18,p1,p18"), ("run_c2a_fast", "p1,p3,p90"), ("run_c2b_tennis", "p7,p20,p24,p29,p32"), ("walk_c2b_stroll", "p4,p11"), ("walk_c2c_saunter", "p4,p5,p11"), ("walk_t2b_stroll", "p4,p11,p21"), ("walk_t2b_stroll", "p4,p11,p5,p25"))

    val prdtData = List(
      ("p1", "nike", "s1", "m", "run", "c2a", "fast", "blue"), ("p2", "nike", "s1", "f", "jog", "c2b", "summer", "pink"), ("p3", "adidas", "s2", "f", "run", "c2a", "tennis", "white"), ("p4", "puma", "s3", "m", "walk", "c2b", "stroll", "red"), ("p5", "nike", "s4", "m", "walk", "c2c", "saunter", "pola blue yellow"), ("p6", "adidas", "s1", "f", "jog", "c2a", "summer", "white"), ("p7", "puma", "s3", "m", "run", "c2b", "tennis", "white"), ("p8", "puma", "s2", "f", "run", "c2a", "badminton", "red"), ("p9", "adidas", "s1", "m", "jog", "c2a", "summer", "white"), ("p10", "adidas", "s2", "m", "run", "c2a", "badminton", "white"), ("p11", "puma", "s2", "m", "walk", "c2a", "office", "black"), ("p12", "nike", "s1", "f", "jog", "t2b", "summer", "blue"), ("p13", "nike", "s1", "f", "walk", "elegant", "office", "black"), ("p14", "nike", "s1", "f", "walk", "t2c", "saunter", "pink"), ("p15", "adidas", "s2", "m", "walk", "t2c", "saunter", "white"), ("p16", "puma", "s3", "m", "walk", "t2c", "saunter", "red"), ("p17", "nike", "s4", "m", "walk", "t2c", "saunter", "pola blue yellow"), ("p18", "nike", "s1", "m", "jog", "t2b", "summer", "blue"), ("p19", "nike", "s1", "f", "jog", "t2b", "summer", "pink"), ("p20", "adidas", "s2", "m", "run", "t2a", "tennis", "white"), ("p21", "puma", "s3", "m", "walk", "t2b", "stroll", "red"), ("p22", "nike", "s4", "m", "walk", "t2c", "saunter", "pola blue yellow"), ("p23", "nike", "s1", "f", "jog", "t2b", "summer", "pink"), ("p24", "adidas", "s2", "m", "run", "t2a", "tennis", "white"), ("p25", "puma", "s3", "m", "walk", "t2b", "stroll", "red"), ("p26", "nike", "s4", "m", "jog", "t2b", "summer", "pola blue yellow"), ("p27", "nike", "s1", "m", "run", "t2a", "fast", "blue"), ("p28", "nike", "s1", "f", "jog", "t2b", "summer", "pink"), ("p29", "adidas", "s2", "m", "run", "t2a", "tennis", "white"), ("p30", "puma", "s3", "m", "jog", "t2b", "summer", "red"), ("p31", "nike", "s4", "m", "walk", "t2c", "saunter", "pola blue yellow1"))

    val prcData = List(
      ("p1", "210", "0"), ("p2", "300", "0"), ("p3", "550", "510"), ("p4", "560", "0"), ("p5", "555", "0"), ("p6", "670", "0"), ("p7", "678", "0"), ("p8", "667", "0"), ("p9", "454", "0"), ("p10", "400", "0"), ("p11", "232", "0"), ("p12", "1090", "0"), ("p13", "100", "0"), ("p14", "1211", "0"), ("p15", "457", "0"), ("p16", "454", "0"), ("p17", "343", "0"), ("p18", "873", "232"), ("p19", "342", "111"), ("p20", "349", "0"), ("p21", "902", "0"), ("p22", "123", "0"), ("p23", "442", "0"), ("p24", "345", "0"), ("p25", "568", "0"), ("p26", "3232", "0"), ("p27", "344", "0"), ("p28", "100", "80"), ("p29", "400", "0"), ("p30", "300", "213"), ("p31", "200", "100"))

    (prdtData, catsData, prcData)
  }

  def getExpected1() = {
     val recomndtnData =  List("p1|p3,p90", "p10|p8", "p11|", "p15|p14,p16,p17,p31,p22", "p16|p14,p15,p17,p31,p22", "p17|p14,p15,p16,p31,p22", "p18|p26,p12,p30,p19,p23", "p20|p29,p24,p32", "p21|p25,p4,p4,p5,p11", "p22|p14,p15,p16,p17,p31", "p24|p29,p20,p32", "p25|p21,p4,p4,p5,p11", "p26|p18,p12,p30,p19,p23", "p29|p20,p24,p32", "p30|p26,p18,p12,p19,p23", "p31|p14,p15,p16,p17,p22", "p4|p11", "p5|p4,p11", "p7|p29,p20,p24,p32", "p9|p18,p18,p12,p6,p1")    
  //  val recomndtnData = List(
    //  ("p9", "p6,p12,p18,p1,p18"), ("p18", "p12,p19,p23,p26,p28"), ("p26", "p12,p18,p19,p23,p28"), ("p30", "p12,p18,p19,p23,p26"), ("p10", "p8"), ("p1", "p3,p90"), ("p7", "p20,p24,p29,p32"), ("p27", ""), ("p20", "p24,p29,p32"), ("p24", "p20,p29,p32"), ("p29", "p20,p24,p32"), ("p11", ""), ("p4", "p11"), ("p5", "p4,p11"), ("p21", "p4,p11"), ("p25", "p4,p11,p21"), ("p15", "p14,p16,p17,p22,p31"), ("p16", "p14,p15,p17,p22,p31"), ("p17", "p14,p15,p16,p22,p31"), ("p22", "p14,p15,p16,p17,p31"), ("p31", "p14,p15,p16,p17,p22"))

    (recomndtnData)

  }
}