package shoes

object Types {
  val ProductsSchema = List('productId, 'brand, 'style, 'gender, 'primaryType_p1, 'type_p2, 'subType_p3, 'color)
  val CatRecoSchema = List('full_cat, 'RecommendedProductIds)

  val PriceSchema = List('productIdPrc, 'maxSalePrice, 'minSalePrice)
  
  def remove(num: Int, list: Array[String]) = list diff List(num)
}