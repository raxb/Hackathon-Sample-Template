## Maiden Payara Hackathon Project Template

This is the base project for the maiden edition of the Payara Hackathon. It is a single module maven project that depends on Jakarta EE and Eclipse MicroProfile as the main APIs. No other third party library is allowed within the scope of the competition. Arquillian and Mockito are already set up. 

### Runtime
The project uses Payara Server 6 Community running on JDK 17, pulled in through the Dockerfile

### Running the project
The project can be packaged as a docker image using the bundled Dockerfile. It also has a docker-compose file that comes with MySQL database should you be interested in setting up a persistent storage for your application (not requirement though). By default, Jakarta Persistence will use an in-memory database from the bundled Payara Server. 
