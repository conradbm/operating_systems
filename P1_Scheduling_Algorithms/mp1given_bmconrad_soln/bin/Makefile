JC =javac -deprecation -classpath .:../machine:../filesys:../userprog:../threads:../vm:/usr/java/lib/rt.jar


all:
	${JC} ../threads/Nachos.java


cleanclass:
	(find .. -name "*.class" -print | xargs rm)


cleanbackup:
	(find .. -name "*~" -print | xargs rm)
