package com.kohls.recommendations
//file paths 
object RPaths {
  val preDir = "./o1/"
  val finalProdRecoCat =  preDir + "prodRecoCat.csv"
  val ProdWithRecoErrs =  preDir + "join_pro-pric-rec_err.csv"
  val PricePipeErrs = preDir + "pricesRead2_err.csv"
  val JoinPricePrdErr = preDir + "join_price-prod_err.csv"
  val ProdAfterJoin = preDir + "pr-podcustAfterJoin3.csv"
  val ProdCatErrs = preDir + "join_pro-rec_err.csv"
  val joinProdRecoCat =  preDir + "joinProdRecoCat.csv"
  val prodAfterCatJoin = preDir + "prpodcust1AfterRecoJoin1.csv"
}