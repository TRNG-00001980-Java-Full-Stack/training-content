### What is Service Discovery (Eureka)?

Service Discovery is a core component of a microservice architecture, which allows services to dynamically register themselves and discover other services without hard-coding their network locations. **Eureka**, a part of the Netflix OSS stack, is a popular **Service Discovery** tool used in Spring Boot applications. It provides service registration and discovery capabilities, enabling microservices to automatically detect and communicate with each other.

In this guide, I'll walk you through setting up **Eureka Server** and two **Spring Boot services** that register themselves with Eureka and use service discovery to communicate.

---

## Step-by-Step Setup Guide for Eureka

### 1. Eureka Server Setup

**Eureka Server** is a Spring Boot application that acts as a service registry. Other microservices register themselves with this server, and it helps them discover each other.

#### Step 1: Create a Eureka Server Project

You can either use **Spring Initializr** or manually create a new Maven or Gradle Spring Boot project.

- Go to **https://start.spring.io/**
- Select **Maven Project**
- Add the following dependencies:
  - **Eureka Server**
  - **Spring Web**

#### Step 2: Add Dependencies in `pom.xml`

Make sure the `pom.xml` has the following dependencies for a Maven project:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2021.0.1</version> <!-- Adjust this to the latest version -->
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

#### Step 3: Enable Eureka Server

In your **main application class**, add the `@EnableEurekaServer` annotation to enable Eureka functionality.

```java
package com.example.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

#### Step 4: Configure `application.properties`

```properties
spring.application.name=eureka-server
server.port=8761

# Eureka Server Configuration
eureka.client.register-with-eureka=false  # The server does not register itself
eureka.client.fetch-registry=false        # The server doesn’t fetch any service info
```

#### Step 5: Run Eureka Server

Once everything is configured, run the Eureka server (`mvn spring-boot:run`). You can access the Eureka dashboard at:

```
http://localhost:8761
```

---

### 2. Microservice (Client) Setup

We will now configure two services (e.g., `service-a` and `service-b`) to register themselves with the Eureka Server and use it for service discovery.

#### Step 1: Create Two Spring Boot Services

Using Spring Initializr or manually create two separate Spring Boot applications:
- **Service A**
- **Service B**

For each service, add the following dependencies:
- **Spring Web**
- **Eureka Discovery Client**

#### Step 2: Add Dependencies in `pom.xml`

For both services, make sure your `pom.xml` includes the following:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

#### Step 3: Enable Discovery Client

In the **main class** of each service, enable Eureka Client by adding `@EnableEurekaClient`:

```java
package com.example.servicea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceAApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAApplication.class, args);
    }
}
```

#### Step 4: Configure `application.properties` for Service A and B

In **Service A** and **Service B**, specify the Eureka server URL and necessary configurations:

##### Service A: `application.properties`

```properties
spring.application.name=service-a
server.port=8081

# Eureka Client Configuration
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

##### Service B: `application.properties`

```properties
spring.application.name=service-b
server.port=8082

# Eureka Client Configuration
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

#### Step 5: Adding REST API Endpoints

In **Service A**, let's add a simple REST controller.

```java
package com.example.servicea.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceAController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Service A!";
    }
}
```

Similarly, in **Service B**, we’ll create another REST controller:

```java
package com.example.serviceb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceBController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Service B!";
    }
}
```

---

### 3. Testing Service Discovery

#### Step 1: Run All Services

- Run **Eureka Server** (`mvn spring-boot:run`)
- Run **Service A** and **Service B**

#### Step 2: Check Eureka Dashboard

Go to `http://localhost:8761`. You should see **Service A** and **Service B** registered with the Eureka Server.

#### Step 3: Access Services via Discovery

Since both services are registered with Eureka, you can access them using their service names.

For example, if you want to access **Service A** from **Service B**, you can use the service name `service-a` instead of `localhost:8081`.

---

### 4. Inter-Service Communication

If you want **Service B** to call an API of **Service A**, you can use Spring’s **RestTemplate** with Eureka.

In **Service B**, configure `RestTemplate` to discover and call **Service A**:

```java
package com.example.serviceb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServiceBController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/call-service-a")
    public String callServiceA() {
        String serviceAUrl = "http://service-a/hello";  // Use service name
        return restTemplate.getForObject(serviceAUrl, String.class);
    }
}
```

In the `main` class of **Service B**, add the `RestTemplate` bean:

```java
@Bean
@LoadBalanced  // This annotation allows RestTemplate to resolve service names using Eureka
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

---

### Conclusion

This tutorial demonstrates how to set up **Spring Cloud Netflix Eureka** for **service discovery** in a microservices architecture. Eureka helps your services register with a central server and discover other services without manually configuring each service's network address. By doing this, you create a more scalable and dynamic infrastructure for microservices, making your application resilient and easy to manage.