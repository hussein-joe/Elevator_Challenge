# Elevator_Challenge

A backend for elevator challenge, it is developed using the following technologies

- Java 8
- JPA / Hibernate
- EJB
- RESTful web services
- Wildfly 10
 
I used Junit, Hamcrest, and Mockito for unit testing. And Arquillian for integration testing.
I am using SQL Server version 2014.

I Uploaded the schema intialization script "schema_init.sql".

It is not explicitly required to move subset of passengers, so I assume that all passengers who want to move from a level to another will be moved in only one elevator.

I followed TDD, and SOLID principles.

### Alternative solutions

There is another solution to move passengers in many steps, so search for the nearest elevator, allow some passengers to use till the max capacity. And search for another nearest elevator for the remaining passengers.
To implement this solution I will need to do the following 

- Extend class NearestElevatorSelector, override few methods. 
- Change the factory class ElevatorSelectorFactory to return this class instead of NearestElevatorSelector.
- Extend ElevatorSelectionResult to add a list of elevators to be used to move passengers.



### Installation

I am using Wildfly 10 to run integration tests, the following steps are required to deploy
- Run script file "schema_init.sql" to create and initialize database.
- Install SQL server JDBC driver (I am using version 4.1)
- Add a datasource to the created database with JNDI name "java:/jboss/datasources/elevator_challenge"



