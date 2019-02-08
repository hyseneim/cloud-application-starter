# Cloud Application Starter: Spring Cloud + Authentication & Authorization Provider (AAP)
To explain it in detail I decided to build a very simple project that 
demonstrates the power of [Spring Cloud](http://spring.io/projects/spring-cloud) and the (objective) beauty of an 
Authentication & Authorization Provider, all [microservices](https://martinfowler.com/articles/microservices.html) contained in a 
dedicated Docker container that talk to each other and bootable 
via docker-compose easily.

For more information I suggest you read the article 
[Deep dive into Microservices Architecture: Spring Cloud connected to an Identity Provider](https://medium.com/@hyseneim/deep-dive-into-microservices-architecture-spring-cloud-connected-to-an-authentication-43e768faca3c) 
published in Medium.

### Requirements

- [Oracle Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

- [Maven v [3.5.3]](http://maven.apache.org/download.cgi)

- [Docker v [17.x/18.x]](https://docs.docker.com/install/#supported-platforms)

### Configurations
After cloning the repository, you should run the following command from the 
project root directory.

```shell 
mvn clean install -DskipTests
```

The execution of the previous command installs the artifact of all the 
modules on its local repository, operation required for the next 
command that builds the docker images.

The following command builds docker images
```shell
mvn -pl cloud-application-config,cloud-application-discovery,cloud-application-gateway,cloud-application-oauth2-authorization-server,cloud-application-microservice-one \
package dockerfile:build
```

If we wanted to verify that the build of the docker images was successful, you 
could execute the following command.

```shell
docker images cloud-application/*
```

You should get a result similar to the following As you can see, there are images 
of the following modules:

1. cloud-application-microservice-one
2. cloud-application-oauth2-authorization-server
3. cloud-application-gateway
4. cloud-application-discovery
5. cloud-application-config


```shell
REPOSITORY                                                        TAG                 IMAGE ID            CREATED             SIZE
cloud-application/cloud-application-microservice-one              latest              2c388e3b3d67        12 minutes ago      148MB
cloud-application/cloud-application-oauth2-authorization-server   latest              9fe3f1120302        12 minutes ago      164MB
cloud-application/cloud-application-gateway                       latest              c3f0f0beafd5        12 minutes ago      149MB
cloud-application/cloud-application-discovery                     latest              81cbc88779e8        12 minutes ago      150MB
cloud-application/cloud-application-config                        latest              018934a5aded        12 minutes ago      150MB
```

### Start
```shell 
docker-compose up -d cloud-application-db
docker-compose up -d cloud-application-config
docker-compose up -d cloud-application-discovery
docker-compose up -d cloud-application-gateway
docker-compose up -d cloud-application-oauth2-authorization-server
docker-compose up -d cloud-application-microservice-one
```

### After Start
Install Postman and import my collection (Cloud_Application.postman_collection.json) of services placed on root of the project.

Request access token and use it to call the Hello World service.

Enjoy! :D


