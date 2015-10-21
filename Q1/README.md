Scalding Product recomendations with sort and take.

Loads data from 3 files.
Filters and transforms Products to add price and recommendataions based on category

See com.kohls.recommendations.r.RecommendRunner (Main class, standalone run)

Sample args (these files are in this repo) :
--local --input o1/com.kohls.recommendations-products.csv --inRCats o1/recomdsCats.csv --inPrice o1/price.csv --debug true  --output o1/pr-podcustAfterJoin3.csv

In Eclipse Scala, choose Scala runtime and compiler 2.10 and Java run time 7

For part 1a - recommended products main class is com.kohls.recommendations.impl.ReccommendProducts
For question 1b - based on price, main class is com.kohls.recommendations.impl.ReccommendProductPrices

Both extend PipeCommon, which encapsulates the common, loading of product and category based recommendation feeds and filtering out non male.