#!/bin/bash
#mvn clean install
echo "Need java 7 , croaks with 8"
export DRIVEN_API_KEY=D991A15E7A174E098900CDEE4F3A3CA6

# export PATH=/c/apps/Java/jdk1.7.0_79/bin:/c/Users/tushar.kapila/bin:/mingw64/bin:/usr/local/bin:/usr/bin:/bin:/mingw64/bin:/usr/bin:/c/apps/dev/Python/p27:/c/apps/dev/Python/p27/Scripts:/c/Users/tushar.kapila/bin:/mingw64/bin:/usr/local/bin:/usr/bin:/bin:/mingw64/bin:/usr/bin:/c/apps/dev/Python/p27:/c/apps/dev/Python/p27/Scripts:/c/WINDOWS/system32:/c/WINDOWS:/c/WINDOWS/System32/Wbem:/c/WINDOWS/System32/WindowsPowerShell/v1.0:/cmd:/c/Program Files (x86)/Skype/Phone:/c/apps/dev/scala/bin:/c/apps/dev/scalaRelated/sbt/bin:/c/apps/Java/jdk1.8.0_60/bin:/c/apps/dev/Python/p27:/c/apps/dev/Python/p27/Scripts:/c/ProgramData/Oracle/Java/javapath:/c/WINDOWS/system32:/c/WINDOWS:/c/WINDOWS/System32/Wbem:/c/WINDOWS/System32/WindowsPowerShell/v1.0:/cmd:/c/Program Files (x86)/Skype/Phone:/c/apps/dev/scala/bin:/c/apps/dev/scalaRelated/sbt/bin:/c/apps/Java/jdk1.8.0_60/bin:/c/apps/Java/maven/maven3.3.3/bin:/usr/bin/vendor_perl:/usr/bin/core_perl

#java -cp target/chapter4-0-jar-with-dependencies.jar -Xmx10G adtargeting.Runner --input log_file.tsv --local

#java -cp target/Q1-1-jar-with-dependencies.jar -Xmx1G  --input log_file.tsv --local
#-Xmx1G
export classpath=target/Q1-1.jar:../run/libs/*:../run/libs
export c=target/Q1-1.jar:../run/libs/*:../run/libs
echo $c
# -classpath target/Q1-1.jar;../run/libs/*;../run/libs
java -classpath $c shoes.m.RecommendRunner  --local --input o1/shoes-products.csv --inRCats o1/recomdsCats.csv --inPrice o1/price.csv --debug true  --output o1/pr-podcustAfterJoin3.csv


