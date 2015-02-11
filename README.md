
[ ![Codeship Status for senare/apbot](https://codeship.com/projects/640237d0-88f4-0132-c963-3ae5e43a70a3/status?branch=master)](https://codeship.com/projects/59558)
[ ![Codeship Status for senare/apbot](https://codeship.com/projects/640237d0-88f4-0132-c963-3ae5e43a70a3/status?branch=stage)](https://codeship.com/projects/59558)

=====

A Perfect Bot

Basic functionality working 

Requires you to have installed and configured correctly 
 * git (obviously)
 * maven, prefferable maven 3.x
 * java, version 8 or so ...
 
1 check out the code i.e git clone

2 enter directory you checked out were the pom.xml is

3 build i.e mvn package

4 edit config i.e config.properties 

	bot.names = here is comma seperated list of character names to use for bots
	<botname>.character.username = username for character <botname>
	<botname>.character.password = password for character <botname>	

5 run i.e using java -jar target/apbot-0.0.1-SNAPSHOT-jar-with-dependencies.jar
 
