# Simple savings account demo 

**Developed with:** [Java 11](https://adoptopenjdk.net/), [Maven 3.6.0](https://maven.apache.org/download.cgi/)
## Start the application

Navigate to the project directory and run:
- mvn clean spring-boot:run 

## About

4 users are available to be used for authentication: `cosmin1,cosmin2,cosmin3`(all with password cosmin) 
and `admin`(with password admin).

After starting the application, open a browser to this address: http://localhost:8080/login, authenticate,
 and try adding an account for the current user.  

The business hours can be changed in the `application.properties` file using the `businesstime` prefix.   
 