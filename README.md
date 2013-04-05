jnlp
====
to launch it you have a number of ways

over jnlp
https://github.com/snasrallah/jnlp/raw/master/jar/HelloWorld.jnlp

locally

via
java -jar jar/HelloWorld.jar

To jar up
=========
for intellij - other users go to where your classes got compiled

cd out/production/jnlp/
jar cfe ../../../jar/HelloWorld.jar HelloWorld HelloWorld.class
then checkin the new jar