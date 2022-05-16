# Adelisa Spring Rest Service

The application is accessible via localhost:8080
Apache Maven version: 3.8.2
Java version: 11
PostgreSQL database
H2 database for tests

### Dependencies
There are a number of third-party dependencies used in the project. 
Browse the Maven pom.xml file for details of libraries and versions used.

### Database setup

Run in psql command prompt:

```
\i 'path/to/project/adelisa-spring-rest-service/src/main/resources/skripta.txt'
```
#### Reminder: 
While starting up postgresql you have to add privileges manually

### How to test
For integration tests:
Open Command Prompt in the folder and run:
```
mvnw clean install -Dspring.profiles.active=integration-test
```

### How to build
Integration tests are included to be executed:
```
mvnw clean install -Dspring.profiles.active=test
```

### How to run
Open Command Prompt in the folder and run for profile -dev:

```
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Alternatively you can use the Spring Boot Maven plugin like so:

```sh
mvnw spring-boot:run
```





