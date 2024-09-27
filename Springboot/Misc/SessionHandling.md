### Spring Boot Session Handling Tutorial

Session handling is crucial in web applications to maintain the state of a user's interaction with the application across multiple requests. Spring Boot provides several ways to manage sessions, including in-memory, JDBC, Redis, and more.

### 1. **Basic Concepts of Session Handling**
- **Session:** A session is a way to store user data between HTTP requests. A session is typically identified by a session ID that is stored in a cookie on the client-side.
- **Session Management:** In Spring Boot, session management can be handled via the `@SessionAttributes` annotation, HTTP session, or Spring Security.

### 2. **In-Memory Session Handling (Default)**
By default, Spring Boot uses in-memory session handling. This is suitable for small applications but not for production as the session data is lost if the application restarts.

#### a. **Setup**
No additional setup is required for in-memory session management. Spring Boot automatically creates and manages sessions for you.

#### b. **Usage Example**
You can use `HttpSession` to store and retrieve session attributes:

```java
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping("/set")
    public String setSession(HttpSession session) {
        session.setAttribute("username", "user1");
        return "Session attribute set";
    }

    @GetMapping("/get")
    public String getSession(HttpSession session) {
        return "Username: " + session.getAttribute("username");
    }
}
```

### 3. **JDBC-Based Session Handling**
For more robust session management, you can store session data in a database using JDBC.

#### a. **Dependencies**
Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-jdbc</artifactId>
</dependency>
```

#### b. **Configuration**
Configure the database and Spring Session in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.session.store-type=jdbc
```

```sql
CREATE TABLE SPRING_SESSION (
    PRIMARY_ID CHAR(36) NOT NULL,
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    EXPIRY_TIME BIGINT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
    SESSION_PRIMARY_ID CHAR(36) NOT NULL,
    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES BYTEA NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);
```


### 4. **Redis-Based Session Handling**
For distributed session management, Redis is commonly used due to its speed and simplicity.

#### a. **Dependencies**
Add the following dependencies to your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

#### b. **Configuration**
Configure Redis in `application.properties`:

```properties
spring.redis.host=localhost
spring.redis.port=6379

spring.session.store-type=redis
```

This setup will store session data in Redis, enabling session sharing across multiple instances of your application.

### 5. **Session Timeout Configuration**
You can configure session timeout in your `application.properties`:

```properties
server.servlet.session.timeout=30m
```

This configuration sets the session timeout to 30 minutes.

### 6. **Session Management with Spring Security**
If you're using Spring Security, session management is often tied to user authentication. You can configure session management with Spring Security as follows:

#### a. **Session Configuration**
In your security configuration class, you can define session management policies:

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .expiredUrl("/session-expired.html")
                .and()
            .and()
            .authorizeRequests()
                .anyRequest().authenticated();
    }
}
```

### 7. **Session Attribute Management with `@SessionAttributes`**
Spring also allows you to manage session attributes using the `@SessionAttributes` annotation:

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@SessionAttributes("user")
public class UserController {

    @ModelAttribute("user")
    public User setUpUserForm() {
        return new User();
    }

    // Other methods
}
```

### 8. **Testing Session Management**
For testing session management, you can use tools like Postman to simulate multiple requests and observe session behavior. Ensure that cookies are enabled to track the session ID.

### **Summary**
Spring Boot provides flexible options for session management, from in-memory storage to using persistent databases like JDBC and Redis. Depending on your application’s needs, you can choose the appropriate session handling mechanism. 
