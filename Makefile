build: *
	MYSQL_URL="jdbc:mysql://localhost/testTable" MYSQL_USER="root" MYSQL_PASSWORD="asdf" mvn compile package exec:java

final:
	mvn compile package exec:java

compile:
	mvn clean compile assembly:single
