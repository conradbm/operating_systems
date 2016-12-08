# JC =javac -deprecation -classpath .:../machine:../filesys:../userprog:../threads:/usr/java/lib/rt.jar
JC =javac -deprecation -classpath .:../machine:../filesys:../userprog:../threads:/usr/local/stow/jdk1.6.0_25/jre/lib/rt.jar

all:
	${JC} ../threads/Nachos.java

cleanclass:
	(find .. -name "*.class" -print | xargs rm)

cleanbackup:
	(find .. -name "*~" -print | xargs rm)

