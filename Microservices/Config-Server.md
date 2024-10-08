Here’s the guide rewritten using `application.properties` instead of `application.yml`:

---

### Setup Guide for Spring Cloud Config Server

#### 1. Setting up a Spring Cloud Config Server

##### Step 1: Add Dependencies

In your `pom.xml` (Maven) or `build.gradle` (Gradle), add the necessary Spring Cloud Config dependencies:

**Maven**
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Hoxton.SR8</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

**Gradle**
```gradle
dependencies {
    implementation 'org.springframework.cloud:spring-cloud-config-server'
}

dependencyManagement {
    imports {
        mavenBom "org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR8"
    }
}
```

##### Step 2: Enable Config Server

In the `main` class of your Spring Boot application (the one annotated with `@SpringBootApplication`), enable the Config Server by adding the `@EnableConfigServer` annotation:

```java
package com.example.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

##### Step 3: Configure the Application Properties

In the `application.properties` file, specify where your configuration files are stored (for example, in a Git repository):

**application.properties (Config Server)**
```properties
server.port=8888

spring.cloud.config.server.git.uri=https://github.com/your-repository/config-repo
spring.cloud.config.server.git.default-label=master
```

**Key properties**:
- `spring.cloud.config.server.git.uri`: The URL of your Git repository where the configuration files are stored.
- `spring.cloud.config.server.git.default-label`: The branch of the Git repository to use (e.g., `master` or `main`).

Alternatively, you can store configurations in a local file system by specifying the `file:` URI.

##### Step 4: Creating a Configuration Repository

Create a separate Git repository (or use an existing one) to store the configuration files. For example, you might have a repository with a folder structure like this:

```
/config-repo
  ├── application.properties
  ├── microservice1.properties
  ├── microservice2-dev.properties
  ├── microservice2-prod.properties
```

- **`application.properties`**: Global configuration for all microservices.
- **`microservice1.properties`**: Configuration specific to a microservice.
- **`microservice2-dev.properties`** and **`microservice2-prod.properties`**: Environment-specific configurations for `microservice2`.

#### 2. Setting Up a Client Microservice

Now that your Config Server is running, you need to configure your microservices to fetch their configuration from it.

##### Step 1: Add Dependencies to the Client Microservice

In the `pom.xml` or `build.gradle` of your client microservice, add the Spring Cloud Config Client dependency:

**Maven**
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
</dependencies>
```

**Gradle**
```gradle
dependencies {
    implementation 'org.springframework.cloud:spring-cloud-starter-config'
}
```

##### Step 2: Configure the Client to Use the Config Server

In the `application.properties` file of the client microservice:

**application.properties (Client Microservice)**
```properties
spring.application.name=microservice1
spring.config.import=configserver:http://localhost:8888
spring.cloud.config.fail-fast=true
```

**Key properties**:
- `spring.application.name`: The name of the microservice (matches the configuration file in the Git repository).
- `spring.cloud.config.uri`: The URL of the Config Server.
- `spring.cloud.config.fail-fast`: If true, the application will fail to start if it cannot connect to the Config Server.

##### Step 3: Accessing Configuration Properties in the Client

The client microservice will now fetch its configuration from the Config Server when it starts. You can access the properties as you normally would in Spring Boot (e.g., using `@Value` or `@ConfigurationProperties` annotations).

For example:

```java
@Value("${custom.property}")
private String customProperty;
```

#### 3. Refreshing Configuration Without Restarting

Spring Cloud Config allows you to refresh the configuration of a running application without restarting it. This can be done using the **Spring Actuator**'s `/actuator/refresh` endpoint.

##### Step 1: Add the Actuator Dependency

Add the Actuator dependency to your client microservice:

**Maven**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

**Gradle**
```gradle
implementation 'org.springframework.boot:spring-boot-starter-actuator'
```

##### Step 2: Enable the `/refresh` Endpoint

In your `application.properties` file, enable the `/refresh` endpoint:

**application.properties (Actuator in Client Microservice)**
```properties
management.endpoints.web.exposure.include=refresh
```

##### Step 3: Refresh the Configuration

Once enabled, you can refresh the configuration by sending a POST request to `/actuator/refresh`:

```bash
curl -X POST http://localhost:8080/actuator/refresh
```

This will reload the configuration from the Config Server without restarting the microservice.

---

This guide now uses `application.properties` throughout for both the Config Server and client microservices. Let me know if you need further clarification!