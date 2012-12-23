non-transactional-tests-mysql-setup
===================================

Simple spring based setup for non-transactional tests for mysql and h2 databases.
This example is based mainly on the https://github.com/nurkiewicz/spring-pitfalls. Due to troubles with Scala-Spring integration I couldn't to fork this repo.

There are two different setups:
- for H2 database - setup based on http://nurkiewicz.blogspot.no/2011/11/spring-pitfalls-transactional-tests.html
- for Mysql database - setup based on http://hwellmann.blogspot.no/2011/12/spring-integration-tests-with-real.html



To run non-transactional tests on H2 database:

mvn clean test


To run non-transaction tests on mysql database:

Of course you need installed mysql server on your machine. Some defaults:
- url:      "jdbc:mysql://localhost:3306/nontransactionaltests"
- username: "root"
- password: ""

This command will run tests against mysql database with the defaults above:

mvn clean test -Dspring.profiles.active=mysql


If you have different Mysql setup you can override these default using -D parameters like this:

mvn clean test -Dspring.profiles.active=mysql -Dmysql.jdbc.url="jdbc:mysql://localhost:3306/nontransactionaltests" -Dmysql.jdbc.username="root" -Dmysql.jdbc.password=""


