### Native Queries in Spring Boot

In Spring Boot, **native queries** are SQL queries that are directly executed against the underlying relational database. Unlike JPQL (Java Persistence Query Language), which operates at the entity level and abstracts the SQL away, **native queries** allow you to write and execute raw SQL queries directly. This can be useful when you need database-specific optimizations or when JPQL cannot fully express your query requirements.

Native queries are written using standard SQL and allow developers to interact with database tables and columns directly, bypassing the abstraction layer provided by JPA and JPQL.

### When to Use Native Queries

- **Complex Queries**: Some queries are too complex to express using JPQL, such as using vendor-specific SQL functions, joins, or subqueries.
- **Performance Optimization**: When a query needs to be fine-tuned for performance and requires database-specific optimizations, native queries can be more efficient than JPQL.
- **Stored Procedures**: If you need to execute stored procedures or functions in the database, native queries allow you to do so.
- **Non-standard SQL Functions**: Sometimes, a database provides functions that are not part of JPQL's standard functionality.
- **Direct Interaction with Database Tables**: In scenarios where the JPA entities do not cover all the necessary database tables or views, native queries can be used to directly query those structures.

### Writing Native Queries in Spring Boot

Native queries in Spring Boot are defined using the `@Query` annotation in a repository interface, with the `nativeQuery` attribute set to `true`. The result can be mapped to entities or custom object types.

#### Example of Native Queries in Spring Boot

Suppose we have a `Car` entity that looks like this:

```java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Car {
    @Id
    @GeneratedValue
    private Long id;
    private String brand;
    private String model;
    private double price;

    // Getters and Setters
}
```

Now, let's define some native queries in a Spring Data JPA repository.

#### Using `@Query` Annotation with Native Queries

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    // Native query to fetch cars based on the brand
    @Query(value = "SELECT * FROM car WHERE brand = :brand", nativeQuery = true)
    List<Car> findCarsByBrand(@Param("brand") String brand);

    // Native query to fetch cars with a price greater than a specified value
    @Query(value = "SELECT * FROM car WHERE price > :price", nativeQuery = true)
    List<Car> findCarsWithPriceGreaterThan(@Param("price") double price);

    // Native query with sorting
    @Query(value = "SELECT * FROM car ORDER BY price ASC", nativeQuery = true)
    List<Car> findAllCarsSortedByPrice();
}
```

#### Executing Native Queries

The methods defined in the repository interface can be invoked like any other repository method. Spring Data JPA will execute the raw SQL query directly against the database.

For example, calling the `findCarsByBrand` method in a service or controller:

```java
@RestController
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/cars/brand/{brand}")
    public List<Car> getCarsByBrand(@PathVariable String brand) {
        return carRepository.findCarsByBrand(brand);
    }

    @GetMapping("/cars/price/above/{price}")
    public List<Car> getCarsByPriceAbove(@PathVariable double price) {
        return carRepository.findCarsWithPriceGreaterThan(price);
    }

    @GetMapping("/cars/sorted")
    public List<Car> getCarsSortedByPrice() {
        return carRepository.findAllCarsSortedByPrice();
    }
}
```

### Mapping Native Query Results

By default, native queries map results to JPA entities, as seen in the examples above. However, if the query result does not map directly to an entity, you can map the result to a **DTO (Data Transfer Object)** or a custom class.

#### Mapping Native Query Results to a DTO

Suppose we have a DTO `CarSummaryDTO` that holds summarized information about a car:

```java
public class CarSummaryDTO {
    private String brand;
    private double averagePrice;

    // Constructor, Getters, and Setters
    public CarSummaryDTO(String brand, double averagePrice) {
        this.brand = brand;
        this.averagePrice = averagePrice;
    }
}
```

You can use a native query to map the result to this DTO using the `@Query` annotation and a custom SQL query:

```java
public interface CarRepository extends JpaRepository<Car, Long> {

    // Native query to calculate the average price per brand
    @Query(value = "SELECT brand, AVG(price) as averagePrice FROM car GROUP BY brand", nativeQuery = true)
    List<Object[]> findAveragePriceByBrand();

    // Alternatively, map the result directly to a DTO using constructor expression
    @Query(value = "SELECT new com.example.CarSummaryDTO(c.brand, AVG(c.price)) FROM Car c GROUP BY c.brand")
    List<CarSummaryDTO> findCarSummaries();
}
```

If you're using the first query (`findAveragePriceByBrand`), you need to manually map the result:

```java
public List<CarSummaryDTO> getCarSummaries() {
    List<Object[]> results = carRepository.findAveragePriceByBrand();
    List<CarSummaryDTO> summaries = new ArrayList<>();
    
    for (Object[] result : results) {
        String brand = (String) result[0];
        double averagePrice = (Double) result[1];
        summaries.add(new CarSummaryDTO(brand, averagePrice));
    }
    
    return summaries;
}
```

Alternatively, you can use JPQL to map directly to the DTO using a constructor expression (`findCarSummaries`).

### Native Queries and Pagination

Spring Data JPA supports pagination for native queries using `Pageable`. This allows you to paginate results easily.

```java
@Query(value = "SELECT * FROM car WHERE brand = :brand", nativeQuery = true)
Page<Car> findCarsByBrand(@Param("brand") String brand, Pageable pageable);
```

You can then invoke this method and pass a `Pageable` object:

```java
Pageable pageable = PageRequest.of(0, 10);  // Page 0, 10 records per page
Page<Car> page = carRepository.findCarsByBrand("Toyota", pageable);
```

### Handling SQL Queries with Joins

Native queries also support joins between tables. For example, if you have a `Car` entity related to an `Owner` entity:

```java
@Query(value = "SELECT c.*, o.name AS ownerName FROM car c JOIN owner o ON c.owner_id = o.id WHERE o.name = :ownerName", nativeQuery = true)
List<Object[]> findCarsByOwner(@Param("ownerName") String ownerName);
```

You would then map the result manually, as shown in previous examples.

### Advantages of Native Queries

1. **Full Control**: Native queries allow you to write complex SQL queries that might be difficult or impossible to express with JPQL or Criteria API.
2. **Vendor-Specific Optimizations**: You can use database-specific features (like functions or optimizations) that are not part of the JPA standard.
3. **Better Performance**: In some cases, writing raw SQL might lead to better performance compared to JPQL, especially for complex queries.

### Drawbacks of Native Queries

1. **Database Dependency**: Since native queries are written in SQL, they are tied to the specific database dialect being used. This can make switching between different database platforms more difficult.
2. **Lack of Abstraction**: Native queries do not leverage the object-oriented abstraction provided by JPA. Developers need to be more mindful of how data is retrieved and mapped back to entities or DTOs.
3. **Manual Result Mapping**: When the query result does not map directly to an entity, you may need to manually map the result set to a DTO or custom class.

### Conclusion

Native queries in Spring Boot offer the flexibility to write raw SQL for interacting with the database directly. They are especially useful for complex queries, performance tuning, and scenarios where JPQL is insufficient. However, because native queries bypass the abstraction provided by JPA, they can tie your application to a specific database and require manual result mapping. Thus, native queries should be used judiciously, typically when JPQL cannot meet your requirements.