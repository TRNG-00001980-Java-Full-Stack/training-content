Springdoc OpenAPI is a library that helps generate OpenAPI documentation for Spring Boot applications. It simplifies the process of creating and maintaining API documentation by automatically generating it from your Spring Boot application’s REST controllers and configuration.

Here’s a comprehensive tutorial on setting up and using Springdoc OpenAPI:

### **1. Setup Dependencies**

Add Springdoc OpenAPI dependencies to your `pom.xml` (for Maven) or `build.gradle` (for Gradle).

**Maven Dependencies**:

```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Springdoc OpenAPI -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-ui</artifactId>
        <version>2.0.0</version> <!-- Use the latest version -->
    </dependency>
</dependencies>
```

**Gradle Dependencies**:

```groovy
dependencies {
    // Spring Boot Starter Web
    implementation 'org.springframework.boot:spring-boot-starter-web'

    // Springdoc OpenAPI
    implementation 'org.springdoc:springdoc-openapi-ui:2.0.0' // Use the latest version
}
```

### **2. Basic Configuration**

Springdoc OpenAPI integrates automatically with Spring Boot. By default, it scans your application for REST controllers and generates API documentation.

**Default URL**: The OpenAPI documentation can be accessed at `/v3/api-docs` and the Swagger UI at `/swagger-ui.html`.

**Example Controller**:

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/users")
    public List<User> getUsers() {
        // Implementation here
        return new ArrayList<>();
    }
}
```

**Access Swagger UI**: Run your Spring Boot application and navigate to `http://localhost:8080/swagger-ui.html`.

### **3. Customizing OpenAPI Configuration**

You can customize the OpenAPI documentation using a configuration class.

**Configuration Class**:

```java
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("public")
            .pathsToMatch("/api/**") // Specify the base path for API endpoints
            .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
            .group("admin")
            .pathsToMatch("/admin/**") // Specify the base path for admin endpoints
            .build();
    }
}
```

### **4. Adding Metadata**

You can add metadata to your API documentation using the `@OpenAPIDefinition` annotation.

**Metadata Configuration**:

```java
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "My API",
        version = "1.0.0",
        description = "Detailed description of My API",
        termsOfService = "https://example.com/terms",
        contact = @Contact(
            name = "John Doe",
            email = "john.doe@example.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://example.com/license"
        )
    )
)
public class OpenApiConfig {
}
```

### **5. Customizing Paths and Tags**

You can customize the paths and tags for the documentation.

**Custom Paths and Tags**:

```java
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "User Management", description = "Operations related to user management")
public class UserController {

    @GetMapping("/users")
    @Tag(name = "User Management")
    public List<User> getUsers() {
        // Implementation here
        return new ArrayList<>();
    }
}
```

### **6. Adding Security Schemes**

You can define security schemes, such as API key authentication or OAuth2.

**Security Scheme Configuration**:

```java
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.security.SecuritySchemeType;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@SecuritySchemes({
    @SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    )
})
public class OpenApiConfig {
}
```

### **7. Configuring Examples and Global Parameters**

You can provide examples for schemas and define global parameters.

**Examples and Global Parameters**:

```java
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSchemas("User", new Schema().example(new ExampleObject().value("{\"name\": \"John Doe\"}"))))
            .info(new Info().title("My API").version("1.0"))
            .addParameters("globalParam",
                new Parameter().name("X-Custom-Header").in(ParameterIn.HEADER).required(true));
    }
}
```

### **8. Handling Profile-Specific Documentation**

You can configure different documentation for different profiles by using Spring’s profile-specific configuration.

**Profile-Specific Configuration**:

**In `application-dev.properties`**:

```properties
springdoc.api-docs.enabled=true
```

**In `application-prod.properties`**:

```properties
springdoc.api-docs.enabled=false
```

### **Summary**

1. **Setup Dependencies**: Add Springdoc OpenAPI dependencies.
2. **Basic Configuration**: Access default documentation and Swagger UI.
3. **Customizing OpenAPI**: Use configuration classes to customize paths and groups.
4. **Adding Metadata**: Use `@OpenAPIDefinition` for additional metadata.
5. **Customizing Paths and Tags**: Define and customize paths and tags for operations.
6. **Adding Security Schemes**: Configure authentication mechanisms.
7. **Configuring Examples and Parameters**: Provide schema examples and global parameters.
8. **Handling Profiles**: Configure documentation per environment.

 For advanced configurations or specific use cases, refer to the [Springdoc OpenAPI documentation](https://springdoc.org).