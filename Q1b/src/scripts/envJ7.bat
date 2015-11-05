Rem on windows maven works with Java 7 minimum, and the project does not compile with Java 8
Rem on Ubuntu, linux, worked with Java 8 too
set JAVA_HOME=C:\apps\Java\jdk1.7.0_79
set Path=C:\apps\Java\jdk1.7.0_79\bin;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\Program Files\Git\cmd;C:\Program Files (x86)\Skype\Phone\;C:\apps\dev\scala\bin;C:\apps\dev\scalaRelated\sbt\\bin;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\Program Files\Git\cmd;C:\Program Files (x86)\Skype\Phone\;C:\apps\dev\scala\bin;C:\apps\dev\scalaRelated\sbt\\bin;C:\apps\Java\maven\maven3.3.3\bin

rem --local --input o1/com.kohls.recommendations-products.csv  --inRCats recomdsCats.csv.txt --debug true