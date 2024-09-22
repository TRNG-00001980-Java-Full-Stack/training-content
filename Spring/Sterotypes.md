### Detailed Explanation of Spring Stereotype Annotations

In Spring, stereotype annotations are used to indicate that a class is a candidate for component scanning and dependency injection. These annotations help to reduce the boilerplate configuration code by automatically detecting and registering beans in the Spring application context. The primary stereotype annotations are:

1. **@Component**
2. **@Service**
3. **@Repository**
4. **@Controller**

Each of these annotations serves a specific purpose and is used in different layers of a typical Spring application. Below, we'll explore each annotation in detail.

### 1. @Component

The `@Component` annotation is a generic stereotype annotation indicating that a class is a Spring component. It can be used on any class to make it a Spring bean.

**Example:**

```java
import org.springframework.stereotype.Component;

@Component
public class MyComponent {
    public void doSomething() {
        System.out.println("Doing something in MyComponent");
    }
}
```

In this example, the `MyComponent` class is marked with the `@Component` annotation, making it a Spring bean. It will be automatically detected and registered in the Spring application context.

### 2. @Service

The `@Service` annotation is a specialization of the `@Component` annotation. It is used to mark a class as a service layer component. The service layer contains business logic and service methods.

**Example:**

```java
import org.springframework.stereotype.Service;

@Service
public class MyService {
    public void performService() {
        System.out.println("Performing service in MyService");
    }
}
```

In this example, the `MyService` class is marked with the `@Service` annotation, indicating that it contains business logic and should be treated as a service layer component.

### 3. @Repository

The `@Repository` annotation is another specialization of the `@Component` annotation. It is used to indicate that a class is a Data Access Object (DAO). It also provides additional benefits like automatic exception translation.

**Example:**

```java
import org.springframework.stereotype.Repository;

@Repository
public class MyRepository {
    public void saveData() {
        System.out.println("Saving data in MyRepository");
    }
}
```

In this example, the `MyRepository` class is marked with the `@Repository` annotation, indicating that it is a DAO component responsible for data access operations.

### 4. @Controller

The `@Controller` annotation is a specialization of the `@Component` annotation. It is used to mark a class as a Spring MVC controller. Controllers handle HTTP requests and return responses.

**Example:**

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    
    @GetMapping("/greet")
    @ResponseBody
    public String greet() {
        return "Hello, World!";
    }
}
```

In this example, the `MyController` class is marked with the `@Controller` annotation, indicating that it handles HTTP requests. The `@GetMapping` annotation maps the `/greet` URL to the `greet` method, which returns a greeting message.

### Benefits of Using Stereotype Annotations

1. **Automatic Bean Registration:** Classes annotated with stereotype annotations are automatically detected and registered as beans in the Spring application context.
2. **Clear Intent:** Using specific stereotype annotations like `@Service`, `@Repository`, and `@Controller` makes the code more readable and indicates the role of each component in the application.
3. **Reduced Boilerplate Code:** Stereotype annotations reduce the need for explicit XML configuration, leading to cleaner and more maintainable code.
4. **Integration with Spring's Exception Translation:** The `@Repository` annotation integrates with Spring's exception translation mechanism, converting database-specific exceptions into Spring's DataAccessException hierarchy.

### Custom Stereotype Annotations

You can also create custom stereotype annotations by combining multiple annotations.

**Example:**

```java
import org.springframework.stereotype.Service;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface MyCustomService {
}
```

In this example, the `MyCustomService` annotation combines the functionality of the `@Service` annotation and can be used to mark service layer components.

**Usage:**

```java
@MyCustomService
public class CustomService {
    public void execute() {
        System.out.println("Executing in CustomService");
    }
}
```

### Conclusion

Spring stereotype annotations (`@Component`, `@Service`, `@Repository`, and `@Controller`) are powerful tools for reducing boilerplate configuration and making your application code more readable and maintainable. By using these annotations, you can clearly define the roles of different components in your application and leverage Spring's automatic bean detection and registration capabilities.