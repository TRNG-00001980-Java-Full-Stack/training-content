Spring Boot Actuator is a powerful feature in the Spring Boot framework that provides production-ready features to help you monitor and manage your application. Actuator exposes various endpoints that provide insights into the running application, including health checks, metrics, environment information, and more.

Here's a complete guide to Spring Boot Actuator, covering its setup, available endpoints, security configurations, and customization options.

## **Spring Boot Actuator: Complete Tutorial**

### **1. What is Spring Boot Actuator?**

Spring Boot Actuator is a set of built-in endpoints that provide information about the application’s runtime behavior. These endpoints include:

- **Health**: Check the health of the application.
- **Metrics**: View metrics data such as memory usage, HTTP requests, and more.
- **Info**: Display custom application information.
- **Env**: View properties in the application environment.
- **Loggers**: View and configure the application’s log levels.

### **2. Adding Actuator to Your Spring Boot Application**

To use Spring Boot Actuator, you need to add the `spring-boot-starter-actuator` dependency to your `pom.xml` or `build.gradle` file.

**For Maven (`pom.xml`):**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

**For Gradle (`build.gradle`):**

```groovy
implementation 'org.springframework.boot:spring-boot-starter-actuator'
```

### **3. Enabling Actuator Endpoints**

By default, only a few Actuator endpoints are enabled. You can configure which endpoints are exposed via your `application.properties` or `application.yml` file.

**Example (`application.properties`):**

```properties
# Expose all actuator endpoints
management.endpoints.web.exposure.include=*
```

Alternatively, you can specify specific endpoints to include:

```properties
# Expose specific endpoints
management.endpoints.web.exposure.include=health,info,metrics
```

### **4. Commonly Used Actuator Endpoints**

Here are some of the key Actuator endpoints:

1. **/actuator**: Provides a list of all available Actuator endpoints.
2. **/actuator/health**: Displays the health status of the application.
3. **/actuator/info**: Displays custom application information.
4. **/actuator/metrics**: Displays metrics data.
5. **/actuator/loggers**: Shows and allows management of log levels.
6. **/actuator/env**: Displays properties of the application environment.
7. **/actuator/beans**: Displays a list of all Spring beans in your application.
8. **/actuator/threaddump**: Displays the current thread dump.

### **5. Customizing Actuator Endpoints**

You can customize the behavior of various Actuator endpoints. Here are some common customizations:

#### **Custom Health Indicators**

You can create custom health indicators to provide more specific health checks.

**Example Custom Health Indicator:**

```java
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Custom health check logic
        boolean serverUp = checkServerHealth();
        if (serverUp) {
            return Health.up().withDetail("Custom Service", "Running").build();
        } else {
            return Health.down().withDetail("Custom Service", "Not Available").build();
        }
    }

    private boolean checkServerHealth() {
        // Add health check logic here
        return true;
    }
}
```

#### **Custom Info Endpoint**

You can add custom information to the `/actuator/info` endpoint by configuring your `application.properties` or `application.yml` file.

**Example (`application.yml`):**

```yaml
info:
  app:
    name: MyApp
    description: This is a sample Spring Boot application
    version: 1.0.0
```

### **6. Securing Actuator Endpoints**

By default, Actuator endpoints are not secured. You should configure security to restrict access to sensitive endpoints.

**Example Security Configuration (`application.properties`):**

```properties
# Enable security for all actuator endpoints
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
spring.security.user.name=admin
spring.security.user.password=adminpassword
```

**Securing Endpoints with Spring Security:**

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/actuator/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and().httpBasic();
    }
}
```

### **7. Viewing Metrics and Customizing Metric Tags**

Spring Boot Actuator provides a wide range of metrics for monitoring applications. Metrics can be viewed via the `/actuator/metrics` endpoint.

**Accessing Specific Metric:**

To access a specific metric, use:

```
GET /actuator/metrics/jvm.memory.used
```

**Customizing Metric Tags:**

You can customize metric tags to provide more context to the collected data.

**Example Custom Tag Configuration:**

```java
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "MyApp");
    }
}
```

### **8. Enabling Auditing with Actuator**

Actuator supports audit events, which can be used to track user actions or system events.

**Example Configuration for Auditing:**

```properties
management.endpoints.web.exposure.include=audit-events
```

**Example Code for Publishing Audit Events:**

```java
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CustomAuditService {

    private final AuditEventRepository auditEventRepository;

    public CustomAuditService(AuditEventRepository auditEventRepository) {
        this.auditEventRepository = auditEventRepository;
    }

    public void logEvent(String principal, String eventType, Map<String, Object> data) {
        AuditEvent auditEvent = new AuditEvent(principal, eventType, data);
        auditEventRepository.add(auditEvent);
    }
}
```

### **9. Monitoring with Prometheus and Grafana**

Actuator can integrate with Prometheus for monitoring. Add the following dependency to export metrics in Prometheus format:

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

### **10. Logging Configuration with Loggers Endpoint**

The `/actuator/loggers` endpoint allows you to view and change the log levels of your application at runtime.

**Viewing Loggers:**

```
GET /actuator/loggers
```

**Changing Logger Level:**

```bash
curl -X POST "http://localhost:8080/actuator/loggers/com.example" \
    -H "Content-Type: application/json" \
    -d '{"configuredLevel": "DEBUG"}'
```

### **Conclusion**

Spring Boot Actuator is an essential tool for monitoring and managing your applications. By exposing key runtime information and health checks, it helps you ensure that your applications are running smoothly and securely. This tutorial covered how to set up Actuator, enable and secure endpoints, customize metrics and health checks, and integrate with monitoring tools.
