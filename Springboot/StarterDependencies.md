## Spring Boot Starter Dependencies

Spring Boot simplifies the dependency management process by providing a set of starter dependencies that bundle common dependencies for various functionalities. Here are some commonly used Spring Boot starter dependencies:

1. **spring-boot-starter-web**:
    - Used to build web applications, including RESTful services using Spring MVC.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    ```

2. **spring-boot-starter-data-jpa**:
    - Used to simplify database access and ORM (Object-Relational Mapping) with JPA (Java Persistence API) and Hibernate.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    ```

3. **spring-boot-starter-security**:
    - Used to add Spring Security for authentication and authorization.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    ```

4. **spring-boot-starter-thymeleaf**:
    - Used to integrate Thymeleaf as the template engine for web applications.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    ```

5. **spring-boot-starter-test**:
    - Used to include testing libraries like JUnit, Mockito, and Spring Test.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    ```

6. **spring-boot-starter-actuator**:
    - Used to add production-ready features such as monitoring, metrics, and health checks.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```

7. **spring-boot-starter-validation**:
    - Used to include Bean Validation with Hibernate Validator.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    ```

8. **spring-boot-starter-data-mongodb**:
    - Used to simplify access to MongoDB databases.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    ```

9. **spring-boot-starter-aop**:
    - Used to include Spring AOP (Aspect-Oriented Programming) for aspect-oriented development.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
    ```

10. **spring-boot-starter-mail**:
    - Used to add support for sending email.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    ```

### Spring Initializr

Spring Initializr is an online tool provided by the Spring team to quickly generate a Spring Boot project with the necessary dependencies. It provides a web interface, command-line interface, and integration with IDEs like IntelliJ IDEA and Eclipse.

#### Using Spring Initializr Web Interface

1. **Visit**: Go to the [Spring Initializr](https://start.spring.io/) website.
2. **Project Metadata**: Fill in the project metadata (Group, Artifact, Name, Description, Package Name, Packaging, Java Version).
3. **Dependencies**: Select the dependencies you need for your project by typing in the search bar and adding them.
4. **Generate Project**: Click on the "Generate" button to download the generated project as a ZIP file.
5. **Import into IDE**: Extract the ZIP file and import the project into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).

#### Using Spring Initializr with IntelliJ IDEA

1. **Create New Project**: Open IntelliJ IDEA and select "Create New Project."
2. **Spring Initializr**: Choose "Spring Initializr" from the options.
3. **Project Metadata**: Fill in the project metadata and click "Next."
4. **Dependencies**: Search and select the dependencies you need and click "Next."
5. **Finish**: Click "Finish" to generate and open the project.

#### Using Spring Initializr with Spring CLI

1. **Install Spring CLI**: Make sure you have the Spring CLI installed. You can install it using SDKMAN:
    ```bash
    sdk install springboot
    ```
2. **Generate Project**: Use the Spring CLI to generate a project:
    ```bash
    spring init --dependencies=web,data-jpa,thymeleaf demo
    ```
3. **Import into IDE**: Import the generated project into your preferred IDE.

### Example of Spring Boot Project Setup with Maven

#### `pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.2</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <java.version>11</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

This example `pom.xml` includes the common starter dependencies for building a web application with JPA and Thymeleaf. You can adjust the dependencies according to your project's requirements.

By using Spring Boot starter dependencies and Spring Initializr, you can quickly set up and start developing your Spring Boot applications with the necessary configurations and dependencies.