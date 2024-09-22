## Dependency Injection in Spring

Dependency Injection (DI) is a design pattern used to implement IoC (Inversion of Control), allowing the creation of dependent objects outside of a class and providing those objects to a class in various ways. In Spring, DI can be achieved through three main configurations: Java-based configuration, XML-based configuration, and annotations.

### Types of Dependency Injection

1. **Constructor Injection**: Dependencies are provided through a class constructor.
2. **Setter Injection**: Dependencies are provided through setter methods.
3. **Field Injection**: Dependencies are directly injected into the fields using annotations.

### Java-based Configuration

Java-based configuration uses `@Configuration` and `@Bean` annotations to define beans.

#### Example

1. **Define Beans:**
    ```java
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class AppConfig {
        
        @Bean
        public Service service() {
            return new ServiceImpl();
        }

        @Bean
        public Client client() {
            return new Client(service());
        }
    }
    ```

2. **Service and Client Classes:**
    ```java
    public interface Service {
        void serve();
    }

    public class ServiceImpl implements Service {
        @Override
        public void serve() {
            System.out.println("Service Called");
        }
    }

    public class Client {
        private final Service service;

        public Client(Service service) {
            this.service = service;
        }

        public void doSomething() {
            service.serve();
        }
    }
    ```

3. **Main Application:**
    ```java
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.AnnotationConfigApplicationContext;

    public class Main {
        public static void main(String[] args) {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            Client client = context.getBean(Client.class);
            client.doSomething();
        }
    }
    ```

### XML-based Configuration

XML-based configuration uses an XML file to define beans and their dependencies.

#### Example

1. **beans.xml:**
    ```xml
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">
        
        <bean id="service" class="com.example.ServiceImpl"/>
        
        <bean id="client" class="com.example.Client">
            <constructor-arg ref="service"/>
        </bean>
    </beans>
    ```

2. **Service and Client Classes:**
    ```java
    public interface Service {
        void serve();
    }

    public class ServiceImpl implements Service {
        @Override
        public void serve() {
            System.out.println("Service Called");
        }
    }

    public class Client {
        private final Service service;

        public Client(Service service) {
            this.service = service;
        }

        public void doSomething() {
            service.serve();
        }
    }
    ```

3. **Main Application:**
    ```java
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class Main {
        public static void main(String[] args) {
            ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
            Client client = context.getBean(Client.class);
            client.doSomething();
        }
    }
    ```

### Annotation-based Configuration

Annotation-based configuration uses annotations like `@Autowired` and `@Component` to define and inject dependencies.

#### Example

1. **Service and Client Classes:**
    ```java
    import org.springframework.stereotype.Component;

    public interface Service {
        void serve();
    }

    @Component
    public class ServiceImpl implements Service {
        @Override
        public void serve() {
            System.out.println("Service Called");
        }
    }

    @Component
    public class Client {
        private final Service service;

        @Autowired
        public Client(Service service) {
            this.service = service;
        }

        public void doSomething() {
            service.serve();
        }
    }
    ```

2. **AppConfig Class:**
    ```java
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    @ComponentScan(basePackages = "com.example")
    public class AppConfig {
    }
    ```

3. **Main Application:**
    ```java
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.AnnotationConfigApplicationContext;

    public class Main {
        public static void main(String[] args) {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            Client client = context.getBean(Client.class);
            client.doSomething();
        }
    }
    ```

### Summary

- **Java-based Configuration**: Uses `@Configuration` and `@Bean` annotations to define beans.
- **XML-based Configuration**: Uses an XML file to define beans and their dependencies.
- **Annotation-based Configuration**: Uses annotations like `@Autowired` and `@Component` to define and inject dependencies.

These three approaches provide flexibility in configuring beans and dependencies in a Spring application. The choice of configuration method depends on the specific requirements and preferences of the development team.