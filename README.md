# AusPostReactiveWebFlux

Post code lookup service
A postcode service to show the rest api architecture within Australia postcode data.

This is a sample Java / Maven / Spring Boot Reactive Webflux  (version 1.5.16) application 

About the Service
The service is a postcode lookup service. It has 3 functions:

User can use it to create new postcode-suburb combination, same combination can't be created twice.
Search suburb information by postcode.
Lookup a postcode with given suburb name.
It uses in-memory database (H2) to preload the Australia postcodes, suburbs and states.

How to Run
This application is packaged as a jar which has Tomcat 8 embedded.

You run it using the java -jar postcode-lookup-service-1.0.0-SNAPSHOT.jar command as long as JVM is installed and configured in the system PATH.

Clone this repository
Make sure you are using JDK 1.8 and Maven 3.x
You can build the project and run the tests by running mvn clean install
Get information about system health, configurations, etc.
http://localhost:8081/env
http://localhost:8081/health
http://localhost:8081/info
http://localhost:8081/metrics
Create a new postcode resource
POST http://localhost:8080/api/postcode-detail?postcode=8777&suburb=myplace

Retrieve Postcode from a suburb
GET http://localhost:8080/api/postcode-detail/suburb/malvern east
Retrieve suburbs from a postcode
http://localhost:8080/api/postcode-detail/postcode/3144
To view Swagger 2 API docs
Run the server and browse to http://localhost:8080/swagger-ui.html

To view your H2 in-memory datbase
To view and query the database you can browse to http://localhost:8080/h2-console. JDBC URL: jdbc:h2:mem:customercrud with default username is 'sa' with a blank password.

Further Improvement
Handle the identical suburb with different postcode in different states.
Security
Technology Used
Springboot
Java 8
Apache Commons
