## Commonly Used Spring Boot Annotations

### Core Spring Boot Annotations

* **@SpringBootApplication:** This is a composite annotation that combines `@ComponentScan`, `@Configuration`, and `@EnableAutoConfiguration`. It marks the main class of a Spring Boot application.
* **@ComponentScan:** Tells Spring to scan a base package for components, configurations, and services.
* **@Configuration:** Indicates that a class declaresone or more `@Bean` methods and thus may be processed by the Spring container to generate bean definitions.
* **@EnableAutoConfiguration:** Tells Spring Boot to try to automatically configure your Spring application based on the jar dependencies that you have added.

### Controller Annotations

* **@RestController:** Marks a class as a REST controller, indicating that it handles HTTP requests and produces JSON or XML responses.
* **@RequestMapping:** Maps HTTP requests to handler methods.
* **@GetMapping:** A specialized version of `@RequestMapping` for HTTP GET requests.
* **@PostMapping:** A specialized version of `@RequestMapping` for HTTP POST requests.
* **@PutMapping:** A specialized version of `@RequestMapping` for HTTP PUT requests.
* **@DeleteMapping:** A specialized version of `@RequestMapping` for HTTP DELETE requests.
* **@PathVariable:** Used to extract data from URI templates.
* **@RequestParam:** Used to bind request parameters to method parameters.
* **@RequestBody:** Used to bind JSON or XML request body to method parameters.
* **@ResponseBody:** Used to indicate that the return value of a method should be written directly to the HTTP response body.

### Service Annotations

* **@Service:** Indicates that a class provides business logic.
* **@Autowired:** Used to automatically inject dependencies.
* **@Qualifier:** Used to disambiguate autowiring when there are multiple beans of the same type.

### Data Annotations

* **@Entity:** Indicates that a class is a JPA entity.
* **@Table:** Used to specify the name of the database table for an entity.
* **@Id:** Marks a field as the primary key of an entity.
* **@GeneratedValue:** Used to specify how the primary key value is generated.
* **@Column:** Used to specify column-specific mapping information.
* **@JoinColumn:** Used to specify the join column for a relationship.
* **@ManyToOne:** Indicates a many-to-one relationship.
* **@OneToMany:** Indicates a one-to-many relationship.
* **@OneToOne:** Indicates a one-to-one relationship.
* **@Repository:** Indicates that a class provides data access logic.

### Other Annotations

* **@PropertySource:** Used to load external properties into the Spring environment.
* **@Value:** Used to inject property values into fields or methods.
* **@Component:** A generic stereotype for any Spring-managed component.
* **@Profile:** Used to activate different bean definitions based on profiles.
* **@Conditional:** Used to conditionally include or exclude bean definitions.

This list is not exhaustive, but it covers the most commonly used Spring Boot annotations.
