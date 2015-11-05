rem java -cp target/Q1-1-jar-with-dependencies.jar -Xmx1G com.kohls.recommendations.RecommendRunner  --local --input o1/com.kohls.recommendations-products.csv --inRCats o1/recomdsCats.csv --inPrice o1/price.csv --debug true




java -cp target/Q1-1.jar;..\run\libs\*;..\run\libs\ -Xmx1G com.kohls.recommendations.r.RecommendRunner  --local --input o1/shoes-products.csv --inRCats o1/recomdsCats.csv --inPrice o1/price.csv --debug false  --output o1/pr-podcustAfterJoin3.csv


