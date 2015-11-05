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

Q1/o1 has the input and output files for test run using runner with above ards

----

Question:
Here are some hypothetical problems I formulated, which will help you somewhat explore Scalding/Scala. You can create your unit test data (list of tuples held in vals in your test case), and test out the same. While doing the operations, you may like to think of which is a map operation (like filter), v/s which is a reduce (like, a groupBy) and any optimization needed. Or, how a join operation will work underneath.  The Twitter one-pager API is a good reference apart from the book. Pls let me know if the statements are not clear, or, you need any help otherwise.

https://github.com/twitter/scalding/wiki/Fields-based-API-Reference

Product Catalog Schema -

productId
brand
style
gender
primaryType (p1)
type (p2)
subType (p3)
color

Product Price Schema -

productId
maxSalePrice
minSalePrice

Category-based Top Trending Recommendations -

category (format: p1|p2|p3)
recommendations (format: comma-delimited list of product ids, e.g., 111,222,333,444,555)

Problem Statements – 

(The problems are independent of each other)

1.	For every Men’s product in the catalog, come up with the list of recommended products based on category, and filter out any potential self-recommendations from it.
2.	Re-rank the category-based recommendations based on price (defined as avg. of min/max – either one nullable) asc., and retain only upto top N (say, N = 5) recommendations.

