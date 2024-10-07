### Overview

In this tutorial, we will create three Spring Boot applications:

1. **Eureka Service Discovery** - To manage and locate microservices.
2. **API Gateway** - To route requests to the appropriate microservice.
3. **Sample Spring Boot MVC Application** - A simple application that will expose REST endpoints.

### Prerequisites

- Java 11 or later
- Spring Boot
- Maven
- Docker (optional, for containerization)
- IDE (like IntelliJ IDEA or Eclipse)

### Project Structure

```
microservices
│
├── service-discovery
│   └── (Eureka Server)
│
├── api-gateway
│   └── (API Gateway)
│
└── revshop
    └── (Sample Spring Boot MVC Application)
```

### 1. Create Eureka Service Discovery

**1.1. Setup the Project**

- Use Spring Initializr (https://start.spring.io/) to create a new Spring Boot project.
- Select the following dependencies:
  - Eureka Server
  - Spring Web

**1.2. Configure `application.properties`**

```properties
spring.application.name=service-discovery
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

**1.3. Enable Eureka Server**

Add the `@EnableEurekaServer` annotation in the main application class:

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryApplication.class, args);
    }
}
```

**1.4. Run the Application**

Run the application and access the Eureka dashboard at `http://localhost:8761`.

### 2. Create the API Gateway

**2.1. Setup the Project**

- Create another Spring Boot project using Spring Initializr with the following dependencies:
  - Spring Cloud Gateway
  - Eureka Discovery Client

**2.2. Configure `application.properties`**

```properties
spring.application.name=api-gateway
server.port=8080

# Eureka Server URL
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Gateway Routes
spring.cloud.gateway.routes[0].id=revshop
spring.cloud.gateway.routes[0].uri=lb://revshop
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/cars/**
```

**2.3. Enable Eureka Client**

Add `@EnableEurekaClient` in the main application class:

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

### 3. Create the Sample Spring Boot MVC Application (Revshop)

**3.1. Setup the Project**

- Create a Spring Boot project with the following dependencies:
  - Spring Web
  - Spring Data JPA
  - MySQL Driver
  - Eureka Discovery Client

**3.2. Configure `application.properties`**

```properties
spring.application.name=revshop
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3306/app
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

**3.3. Create a Sample MVC Controller**

Create a simple REST controller to handle requests:

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @GetMapping
    public String getCars() {
        return "List of Cars";
    }
}
```

**3.4. Enable Eureka Client**

Add `@EnableEurekaClient` in the main application class:

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RevshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(RevshopApplication.class, args);
    }
}
```

### 4. Running the Applications

- Start the **Eureka Server** (service-discovery) at `http://localhost:8761`.
- Start the **Revshop Application** (the sample MVC app) at `http://localhost:8081`.
- Start the **API Gateway** at `http://localhost:8080`.

### 5. Testing the API Gateway

- Access the API Gateway by navigating to `http://localhost:8080/api/cars`. The API Gateway will route the request to the Revshop service, returning the list of cars.

### 6. (Optional) Containerization with Docker

**6.1. Create Dockerfile for Each Service**

For each service (Revshop, API Gateway, Service Discovery), create a `Dockerfile`:

```Dockerfile
# Dockerfile
FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

**6.2. Create a `docker-compose.yml`**

```yaml
version: '3'
services:
  service-discovery:
    image: service-discovery
    build:
      context: ./service-discovery
    ports:
      - "8761:8761"

  api-gateway:
    image: api-gateway
    build:
      context: ./api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - service-discovery

  revshop:
    image: revshop
    build:
      context: ./revshop
    ports:
      - "8081:8081"
    depends_on:
      - service-discovery
```

### Conclusion

You now have a complete microservices architecture using Spring Boot with an API Gateway, Service Discovery, and a sample MVC application. You can further expand this architecture by adding more services, implementing load balancing, circuit breakers, and other microservices patterns.
