## Inversion of Control (IoC)

### IoC Container

The Spring IoC container is responsible for managing the lifecycle and configuration of application objects. The container uses dependency injection (DI) to manage the components (beans) that make up an application.

#### Types of IoC Containers

1. **BeanFactory**: The basic IoC container, which is lightweight and suitable for simple scenarios. It lazily instantiates beans.
2. **ApplicationContext**: A more advanced container that builds on `BeanFactory`. It eagerly instantiates beans and provides additional features such as event propagation, declarative mechanisms to create a bean, and various ways to look up a bean.

### Bean Lifecycle

The lifecycle of a Spring bean is managed by the Spring IoC container. The lifecycle includes several stages:

1. **Instantiation**: The container creates an instance of the bean.
2. **Properties Population**: The container sets the properties of the bean.
3. **BeanNameAware**: The bean is aware of its name.
4. **BeanFactoryAware**: The bean is aware of the bean factory.
5. **ApplicationContextAware**: The bean is aware of the application context.
6. **Post-Processing before Initialization**: The bean's `BeanPostProcessor` is invoked before initialization.
7. **InitializingBean**: The `afterPropertiesSet()` method of the `InitializingBean` interface is called.
8. **Custom Initialization**: The bean's custom init method is called.
9. **Post-Processing after Initialization**: The bean's `BeanPostProcessor` is invoked after initialization.
10. **Ready to Use**: The bean is fully initialized and ready to be used.
11. **Destruction**: When the container is closed, the bean's `DisposableBean` interface's `destroy()` method is called, and the custom destroy method is invoked.

### Scopes of Beans

Spring supports several bean scopes:

1. **Singleton**: (Default) Only one instance of the bean is created per Spring IoC container.
2. **Prototype**: A new instance is created every time the bean is requested.
3. **Request**: A new instance is created for each HTTP request (only available in web-aware contexts).
4. **Session**: A new instance is created for each HTTP session (only available in web-aware contexts).
5. **Global Session**: A new instance is created for each global HTTP session (only available in web-aware contexts).

### Bean Definition

A bean definition describes how a bean is created, its lifecycle, dependencies, and configuration metadata. Bean definitions can be defined in several ways:

1. **XML Configuration**:
    ```xml
    <bean id="myBean" class="com.example.MyBean" scope="singleton">
        <property name="property1" value="value1"/>
        <property name="property2" ref="anotherBean"/>
    </bean>
    ```

2. **Java Configuration**:
    ```java
    @Configuration
    public class AppConfig {
        @Bean
        @Scope("singleton")
        public MyBean myBean() {
            return new MyBean();
        }
        
        @Bean
        public AnotherBean anotherBean() {
            return new AnotherBean();
        }
    }
    ```

3. **Annotations**:
    ```java
    @Component
    @Scope("singleton")
    public class MyBean {
        @Autowired
        private AnotherBean anotherBean;
        
        // getters and setters
    }
    ```

### Bean Instantiation

Bean instantiation can be achieved in multiple ways:

1. **Constructor-based Injection**:
    ```java
    public class MyBean {
        private final AnotherBean anotherBean;
        
        @Autowired
        public MyBean(AnotherBean anotherBean) {
            this.anotherBean = anotherBean;
        }
    }
    ```

2. **Setter-based Injection**:
    ```java
    public class MyBean {
        private AnotherBean anotherBean;
        
        @Autowired
        public void setAnotherBean(AnotherBean anotherBean) {
            this.anotherBean = anotherBean;
        }
    }
    ```

### Complete Example

#### Service and Client Classes

```java
public interface Service {
    void serve();
}

@Component
@Scope("singleton")
public class ServiceImpl implements Service {
    @Override
    public void serve() {
        System.out.println("Service Called");
    }
}

@Component
@Scope("singleton")
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

#### Java Configuration

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```

#### Main Application

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

This example demonstrates the core concepts of IoC in Spring, including the IoC container, bean lifecycle, bean scopes, bean definition, and instantiation methods. By understanding these concepts, you can effectively manage dependencies and configurations in a Spring application.