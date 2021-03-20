# Spring Boot Application - ReST API
This is an API to demonstrate;
- **CRUD** operations on Book
- **Checkout** functionality with or without promoCode

### Tech Stack
- *Spring Boot 2*
- *Spring Dev Tools*
- *ReST Service Architecture*
- *Spring Data JPA (Hibernate)*
- *Repository Pattern*
- *Logging*
- *Javax Validation*
- *Exception Handling*
- *Junit - Unit Tests*
- *Docker*
- *OpenApi (formerly called Swagger) for Documentation*

### Prerequisites
- Java 11 or above
- Gradle (6) or Maven (latest)
- IDE IntelliJ IDEA

## Built With
I have tried my best to use the latest(stable) available libraries!

- Java11 (11.0.2)
- Spring Boot2 (2.4.3)
- Spring Data JPA
- Hibernate
- H2 Database
- JUnit5
- Mockito
- Logging
- Dependency Management (1.0.11.RELEASE)
- Docker (20.10.3, build 48d30b5)
- OpenApi (3.0.0)

## Steps for running application
#### Terminal
   - go to **directory/app**
   - execute "**./gradlew clean build**" to generate .jar file
   - execute "**java -jar build/libs/app-1.0.jar**" to run the application

#### JUnits
   - execute "**./gradlew test**"

#### Docker
- To create Docker Image
   - execute **./gradlew bootBuildImage**

- To verify Docker Image
   - execute **docker images** and find the application rootProjectName:version

- To Run application from Docker Image
   - execute **docker run --tty --publish 5555:5555 app:1.0**

## Application URLs

    - http://localhost:5555/swagger-ui/
    - http://localhost:5555/v2/api-docs
    - http://localhost:5555/api/book
    - http://localhost:5555/api/books
    - http://localhost:5555/api/checkout
