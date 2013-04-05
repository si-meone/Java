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

1. compile your module (creates new .classes)

2. cd out/production/jnlp/

3. jar cfe ../../../jar/HelloWorld.jar HelloWorld HelloWorld.class

4. check your jar java -jar ../../../jar/HelloWorld.jar

5.then checkin the new jar and relaunch
