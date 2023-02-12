# AusPostReactiveWebFlux

Post code lookup service
A postcode service to show the rest api architecture within Australia postcode data.

This is a sample Java / Maven / Spring Boot Reactive Webflux  (version 1.5.16) application with shared redis and Docker (for redis, pgadmin, postgresql)

About the Service
The service is a postcode lookup service. It has 3 functions:

User can use it to create new postcode-suburb combination, same combination can't be created twice.
Search suburb information by postcode.
Lookup a postcode with given suburb name.
It uses in-memory database (H2) to preload the Australia postcodes, suburbs.

Assumption 
Record referenced from third party service does have few duplications so have not applied Unique key constraints whereas for test data 
we have done that under resource\h2\postcodes.sql with limited set of records (postcode suburb combination is unique)

These Records are not being inserted frequently, if it is done we need event driven arch while integration with 3rd party service. 

create table postcodedetail (

    id bigint auto_increment,
    postcode int,
    suburb varchar(50),
    primary key (id),

 CONSTRAINT postdetailunique UNIQUE (postcode, suburb)
);


How to Run Locally without docker 

If you want you can install redis in local and configure and make it up and running at port 6379 should be able to connect at localhost/127.0.0.1:6379
For H2 Uncomment pom.xml with h2 dependency  commneted out postgress dependency , same as well in application.property file uncomment h2 and comment postgress and run 
java -jar lookup-service-0.0.1-SNAPSHOT.jar 

How to Run Locally without docker 
run  from terminal from project folder docker compose up 
It will spin up PgAdmin, redis running on 6379 and postgressql 

login to PgAdmin localhost:9000   
admin@admin.com
admin
clck on add server and give host name as postgres , username as postgres , password as postgres 
you will see schema and table with mentioned name (reactive doesn't support at the moment auto creation so we have to do like that)

you can go to redis bash from terminal docker exec -it redis bash 
than redis-cli
than you can check redis hash 

you can run now various end points successfully in secure manner .


This application is packaged as a jar embedded netty.

You run it using the java -jar lookup-service-0.0.1-SNAPSHOT.jar command as long as JVM is installed and configured in the system PATH.

Clone this repository
Make sure you are using JDK 1.8 and Maven 3.x
You can build the project and run the tests by running mvn clean install


Endpoints with request

GET /api/postcode-detail/all HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46YWRtaW4=
Cache-Control: no-cache
Postman-Token: 440c5642-f70b-7a2c-cc8b-ab5a8994cd7d

POST /api/postcode-detail/post HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Authorization: Basic YW55OmFueQ==
Cache-Control: no-cache
Postman-Token: b301cac3-7405-faab-4e94-f93a745cc827

{
	"postcode": 22,
	"suburb" : "sdf"
}

GET /api/postcode-detail/suburb/COBURN HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
Postman-Token: 02e8a997-aff8-1d62-b337-ff96697e79d9

GET /api/postcode-detail/postcode/1011 HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
Postman-Token: 66832f0f-767f-db40-52d3-af485b0e82b6

Rest Other endpints have been created for put and delete and few get 



POST http://localhost:8080/api/postcode-detail?postcode=8777&suburb=myplace

Current Design
Used Spring webflux Reactive strem for some endpoints to depict Non Blocking I/O behavior if concuurent threads from client app than it can scale better,
Further used redis cache as well with Scheduler which fetches every 15 seconds from DB and Put it in Hash Redis , which can be used with access endpoints
(few endpoints for redis consideratino has been skipped but can be done) , This will have High Throughput and Lower laterncy , we could have used Cache Aside Pattern as well. Used Docker for Redis,PgAdmin, Postgress to Spin up and play around with different endpoints, Implementated Basic Security for Post 
and get endpoints , Admin only allowed for POST,PUT,DELETE endpoints and For GET endpoints user and admin both are allowed to access. You will get 401 ,403 etc client server error depends on auth config. 


Further Improvement..
Test can be writen with more coverage. 
Desing Consideration and To Do further- Server side event or Redis Pub Sub model can be used if integrationg with third party and any update in Source DB to notify consumer about update and take action. 




