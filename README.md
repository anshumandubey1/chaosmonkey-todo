# Todo REST Application using Spring-Boot

> _**Note:**_ PostgreSQL is used as database here.

## Task
- Get the TODO data from the DB
- Create a new TODO
- Update an existing TODO
- Delete a TODO

## Setup
- Create file `gradle.properties` using `sample.gradle.properties` as reference.
- Enter PostgreSQL database `url`, `user` and `password` in the above file.
- Run command `./gradlew flywayMigrate -i` in terminal.
- Create file `secrets.properties` using `sample.secrets.properties` as reference in `\src\main\resources`.
- Use the same `url`, `user` and `password` used in `gradle.properties`.
- Run the application
 