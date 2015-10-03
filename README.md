# Spring Boot REST API TDD

Spring Boot app demonstrating a REST API developed test-first.

* 1 domain model using [Spring data JPA](http://projects.spring.io/spring-data-jpa/)
* 1 controller with 2 actions
* Validation using native [Spring validation](http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/validation.html)
* MySql database see `src/main/resources/{application,application-test}.properties` for connection info
* [Flyway](http://flywaydb.org/) see `src/main/resources/db/migration` for database migrations
  * `./gradlew flywayMigrate` to migrate development database
  * `./gradlew flywayMigrate -Penv=test` to migrate test database
* `spring-boot-starter-test` for jUnit, Hamcrest, and Mockito
* Gradle build system with separate test and integration test source sets
  * `./gradlew test` to run unit tests
  * `./gradlew integrationTest` to run integration tests
