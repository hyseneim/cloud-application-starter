# Cloud Application (Spring Cloud + AAP)

### Requirement

- [Oracle Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

- [Maven v [3.5.3]](http://maven.apache.org/download.cgi)

- [Docker v [3.5.3]](https://docs.docker.com/install/#supported-platforms)

### Configuration
On root project:
```shell 
mvn clean install -DskipTests
```
For each module:
```shell 
mvn package dockerfile:build -f "$MODULE_NAME"
```

### Start
```shell 
docker-compose up cloud-application-db
docker-compose up cloud-application-config
docker-compose up cloud-application-discovery
docker-compose up cloud-application-gateway
docker-compose up cloud-application-oauth2-authorization-server
docker-compose up cloud-application-microservice-one
```

### After Start
Use Postman collection to enjoy services! :D


