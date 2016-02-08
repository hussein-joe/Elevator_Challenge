# Elevator_Challenge

A backend for elevator challenge, it is developed using the following technologies

- Java 8
- JPA / Hibernate
- EJB
- RESTful web services
- Wildfly 10
 
I used Junit, Hamcrest, and Mockito for unit testing. And Arquillian for integration testing.
I am using SQL Server version 2014.

I Uploaded the configured version of Wildfly (installed SQL Server JDBC driver, and datasource) that can be used to run integration tests. Also, uploaded the schema intialization script "schema_init.sql".

It is not explicitly required to move subset of passengers, so I assume that all passengers who want to move from a level to another will be moved in only one elevator.

I followed TDD, and SOLID principles.



