# AUSPOSTREACTIVEWEBFLUX

Post code lookup service
A postcode service to show the rest api architecture within Australia postcode data.

This is a sample Java (Jdk 11) / Maven / Spring Boot Reactive Webflux (Spring 5) application with shared redis and Docker (for redis, pgadmin, postgresql)
along with Spring Reactive Security

ABOUT THE SERVICE
The service is a postcode lookup service.

An API that allows mobile clients to retrieve the suburb information
by postcode.
An API that allows mobile clients to retrieve a postcode given a
suburb name.

A secured API to add new suburb and postcode combinations (you'll
have to work out how this should work)

Some form of persistence.

ASSUMPTION 
Record referenced from third party service does have few duplications so have not applied Unique key constraints whereas for test data 
we have done that,under resource\h2\postcodes.sql with limited set of records (postcode suburb combination is unique)
If 3rd Party API these records updated frequently than  we need event driven arch while integration with 3rd party service.
we have taken sample dump to start persistance.

create table postcodedetail (

    id bigint auto_increment,
    postcode int,
    suburb varchar(50),
    primary key (id)
    CONSTRAINT postdetailunique UNIQUE (postcode, suburb)
);


HOW TO RUN LOCALLY WITH DOCKER (RECOMMENDED)
run  from terminal from project folder 
docker compose up 

It will spin up PgAdmin, redis running on 6379 and postgressql 

login to PgAdmin from url localhost:9000   
username : admin@admin.com
password: admin
clck on add server and give host name as postgres , username as postgres , password as postgres 
you will see schema and table with mentioned name  (reactive doesn't support at the moment auto creation so we have to do like that)

you can go to redis bash from terminal docker exec -it redis bash 
than redis-cli
than you can check redis hash 

you can run now various end points successfully in secure manner if get 4XX
than set Basic Auth from postman with various combinations to play around

username admin 
password admin

username user
password user

username any
password any



// RUN WITH H2 IN MEMORY DB LOCALLY WITHOUT DOCKER :

If you want you can install redis in local and configure and make it up and running at port 6379 should be able to connect at localhost/127.0.0.1:6379.
For H2 Uncomment pom.xml with h2 dependency and commneted out postgress dependency , same as well in application.property file uncomment h2 and comment postgress and run 
java -jar lookup-service-0.0.1-SNAPSHOT.jar 

or import in Intellij or your faviroute tool and Run main Application LookupServiceApplication. 


ENDPOINT WITH REQUESTS :

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

Rest Other endpints have been created for put and delete and few gets for extendibility further ..

Few Sample Postcode snippet after running

<img width="946" alt="image" src="https://user-images.githubusercontent.com/16664076/218326127-ec6a21a9-3c8e-428d-9a06-756856bebf39.png">

403 Access denier wrong username password for post
<img width="1100" alt="image" src="https://user-images.githubusercontent.com/16664076/218327429-db688961-caa0-4d72-a988-fcb216330575.png">

401 without Basic Auth
<img width="1084" alt="image" src="https://user-images.githubusercontent.com/16664076/218327456-f623ed2d-34e9-40dc-bf3b-34279279c122.png">

200 with Admin User Basic Auth
<img width="1096" alt="image" src="https://user-images.githubusercontent.com/16664076/218327497-05a09ee8-44d3-477d-9ae4-a039a28a7410.png">

200 Get Postcode details  by Subrub name (user and admin only allowed to have this running)
<img width="1084" alt="image" src="https://user-images.githubusercontent.com/16664076/218327563-05878566-4cdf-4a9f-9c3b-3750f0b9888d.png">

200 Get Suburb details by postCode name ( user and admin only will allow to have this running)
<img width="1089" alt="image" src="https://user-images.githubusercontent.com/16664076/218327630-4759b51e-655c-418f-b5c1-4ca09dc5e2a0.png">




CURRENT DESIGN :

Used Spring webflux Reactive strem for some endpoints to depict Non Blocking I/O behavior ,if concuurent threads from client app than it can scale better as non blocking,  Further used redis cache as well with Scheduler which fetches every 15 seconds from DB and Put it in Hash Redis , which can be used to access endpoints
(few endpoints for redis consideratino has been skipped but can be done) , This will have High Throughput and Lower laterncy , we could have used Cache Aside Pattern as well. 

Used Docker for Redis,PgAdmin, Postgress to Spin up and play around with different endpoints, 

Implementated Basic Security for Post 
and get endpoints , Admin only allowed for POST,PUT,DELETE endpoints and For GET endpoints user and admin both are allowed to access. 

You will get 401 ,403 etc client server error depends on auth config. 


FURTHER IMPROVEMENT CAN BE DONE.. :

Test can be writen with more coverage..
Desing Consideration and To Do further- Server side event or Redis Pub Sub model can be used if integrating with third party and any update in Source DB to notify consumer about update and take action. 
Could have used Parallel Stream for fetching records in stream pipeline.
Custom Exception handling could have been done and few inputs and outputs validations ( right now we are sending 2xxx, and for Auth eror 4XX etc)
we can return list of Postcodes or Suburbs as well if needed to the client rather than combinations of postcode and suburb and can swith between json vs stream .. 
Can use profile etc for various environment and deployed in ECS/Farget..





