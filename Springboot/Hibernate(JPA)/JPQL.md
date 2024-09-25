### JPQL (Java Persistence Query Language) in Spring Boot

JPQL (Java Persistence Query Language) is a powerful query language defined as part of JPA (Java Persistence API). It is used to create queries against entity objects stored in a relational database. Unlike SQL, which works with tables and columns, JPQL works with entities and their attributes. This makes it object-oriented and allows for a cleaner interaction with the database.

In Spring Boot, JPQL is extensively used in repositories for querying database entities. It allows developers to create custom queries in a clean, expressive way, while still leveraging the object-relational mapping (ORM) system provided by JPA.

### Key Features of JPQL

1. **Object-Oriented Queries**: JPQL queries are written based on the entity class names and field names rather than the table and column names in the database.
2. **Standardized**: JPQL is part of the JPA specification, meaning that any JPA-compliant persistence provider (such as Hibernate) supports it.
3. **Database Independence**: JPQL abstracts away the underlying database and helps to create queries that can work across different databases.
4. **Integration with JPA Entities**: JPQL is tightly integrated with JPA entity mappings, relationships, and inheritance.

### Basic JPQL Syntax

A JPQL query looks similar to SQL but is written using the entity and attribute names. A typical JPQL query has the following syntax:

```sql
SELECT entityAlias FROM EntityName entityAlias WHERE condition
```

- `EntityName`: The name of the entity (not the table) to query.
- `entityAlias`: An alias for the entity in the query.
- `condition`: The WHERE clause for filtering data based on conditions.

### Example of JPQL in Spring Boot

Assume we have the following `Car` entity:

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

#### Writing a JPQL Query Using `@Query` Annotation

The `@Query` annotation in Spring Data JPA allows you to write JPQL queries in your repository interface.

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    // JPQL query to fetch all cars of a specific brand
    @Query("SELECT c FROM Car c WHERE c.brand = :brand")
    List<Car> findCarsByBrand(@Param("brand") String brand);

    // JPQL query to fetch all cars with a price greater than a specified value
    @Query("SELECT c FROM Car c WHERE c.price > :price")
    List<Car> findCarsWithPriceGreaterThan(@Param("price") double price);

    // JPQL query to fetch cars sorted by price
    @Query("SELECT c FROM Car c ORDER BY c.price ASC")
    List<Car> findAllCarsSortedByPrice();
}
```

#### Executing Queries in Spring Boot

- The methods defined in the repository interface can be invoked directly from your service or controller layer.
- Spring Data JPA automatically handles query execution and returns the result.

For example:

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

### JPQL Keywords and Expressions

JPQL supports various keywords and expressions to build powerful queries. Here are some of the most commonly used features:

#### 1. **Selecting Data**
```sql
SELECT e FROM Entity e
```
- The basic syntax for selecting data from an entity.

#### 2. **Filtering Data Using WHERE**
```sql
SELECT e FROM Entity e WHERE e.attribute = :value
```
- Use the `WHERE` clause to filter data based on conditions.

#### 3. **Using Logical Operators**
```sql
SELECT e FROM Entity e WHERE e.attribute = :value AND e.anotherAttribute = :anotherValue
SELECT e FROM Entity e WHERE e.attribute = :value OR e.anotherAttribute = :anotherValue
```
- Logical operators like `AND`, `OR`, and `NOT` are used for combining conditions.

#### 4. **Using Comparison Operators**
```sql
SELECT e FROM Entity e WHERE e.attribute > :value
SELECT e FROM Entity e WHERE e.attribute <= :value
```
- JPQL supports standard comparison operators like `>`, `<`, `>=`, and `<=`.

#### 5. **Using `LIKE` for Pattern Matching**
```sql
SELECT e FROM Entity e WHERE e.attribute LIKE :pattern
```
- Use `LIKE` for pattern matching in JPQL. For example, `%` represents any sequence of characters, and `_` represents a single character.

#### 6. **Sorting Results Using `ORDER BY`**
```sql
SELECT e FROM Entity e ORDER BY e.attribute ASC
SELECT e FROM Entity e ORDER BY e.attribute DESC
```
- Use `ORDER BY` to sort results in ascending (`ASC`) or descending (`DESC`) order.

#### 7. **Pagination Using `LIMIT` and `OFFSET`**
While JPQL does not support `LIMIT` and `OFFSET` directly, you can use Spring Data JPA’s `Pageable` functionality to paginate results.

```java
@Query("SELECT c FROM Car c WHERE c.brand = :brand")
Page<Car> findCarsByBrand(@Param("brand") String brand, Pageable pageable);
```

#### 8. **Using `JOIN` for Relationships**
JPQL supports both inner and outer joins. When two entities have a relationship, you can use `JOIN` to fetch related data.

For example, if `Car` had an `Owner` relationship:

```java
SELECT c FROM Car c JOIN c.owner o WHERE o.name = :ownerName
```

#### 9. **Aggregation Functions**
You can use aggregation functions like `COUNT`, `SUM`, `AVG`, `MAX`, and `MIN` in JPQL queries.

```sql
SELECT COUNT(c) FROM Car c
SELECT AVG(c.price) FROM Car c
```

#### 10. **Named Queries**
JPQL queries can be defined as **named queries** either in the entity class or in `orm.xml`.

For example, in the entity class:

```java
@Entity
@NamedQuery(name = "Car.findByBrand", query = "SELECT c FROM Car c WHERE c.brand = :brand")
public class Car {
    // Entity fields
}
```

You can use named queries in repositories like this:

```java
List<Car> findByBrand(@Param("brand") String brand);
```

### Advantages of JPQL in Spring Boot

1. **Object-Oriented**: JPQL allows querying the database using entity attributes instead of table columns, making it easier for developers to work with.
2. **Abstraction from SQL**: Developers do not need to worry about the underlying database or write native SQL queries unless necessary.
3. **Seamless Integration with Spring Data**: JPQL is integrated into Spring Data JPA, which makes it easy to write queries directly within repositories.
4. **Portability**: Since JPQL is part of the JPA specification, it is independent of the underlying database, ensuring that applications can easily switch between databases.

### When to Use Native Queries Over JPQL?

In some cases, you may need to use **native SQL queries** instead of JPQL:

- When the query is too complex to express with JPQL.
- When performance optimization is necessary (e.g., using database-specific optimizations).
- When you need to use database-specific functions or constructs not supported by JPQL.

Native queries can be defined using the `@Query` annotation with `nativeQuery = true`:

```java
@Query(value = "SELECT * FROM car WHERE brand = :brand", nativeQuery = true)
List<Car> findCarsByBrandNative(@Param("brand") String brand);
```

### Conclusion

JPQL is a powerful query language for working with JPA entities in Spring Boot. It provides a cleaner, object-oriented way to interact with databases and integrates seamlessly with Spring Data JPA repositories. By understanding its syntax and capabilities, developers can write efficient and maintainable queries for their Spring Boot applications.