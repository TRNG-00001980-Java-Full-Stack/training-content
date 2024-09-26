Hibernate supports object-relational mapping (ORM) for Java classes, including inheritance hierarchies. When mapping inheritance, Hibernate provides several strategies to manage how Java class hierarchies are represented in the relational database. Understanding these strategies helps in choosing the right approach based on your application needs.

### **Inheritance Mapping Strategies in Hibernate**

Hibernate supports the following inheritance mapping strategies:

1. **Single Table Strategy (Table per Hierarchy)**
2. **Joined Table Strategy (Table per Subclass)**
3. **Table per Concrete Class (Table per Class)**
4. **Mapped Superclass Strategy**

Let's dive into each strategy with explanations, advantages, disadvantages, and examples.

### **1. Single Table Strategy (Table per Hierarchy)**

This strategy maps the entire class hierarchy into a single database table. A discriminator column is used to distinguish between different subclasses.

#### **Advantages:**
- Simplifies schema with a single table.
- Efficient querying as all data is in one table.

#### **Disadvantages:**
- Wastes space for nullable columns for fields not used by all subclasses.
- Difficult to enforce certain constraints, like `NOT NULL` for specific subclass attributes.

#### **Example:**

```java
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Getters and Setters
}

@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle {
    private int numberOfDoors;

    // Getters and Setters
}

@Entity
@DiscriminatorValue("BIKE")
public class Bike extends Vehicle {
    private boolean hasPedals;

    // Getters and Setters
}
```

**Generated Table:**

| ID | NAME      | VEHICLE_TYPE | NUMBER_OF_DOORS | HAS_PEDALS |
|----|-----------|--------------|-----------------|------------|
| 1  | Sedan     | CAR          | 4               | NULL       |
| 2  | Mountain  | BIKE         | NULL            | TRUE       |

**Annotations Used:**
- `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`: Specifies the inheritance strategy.
- `@DiscriminatorColumn`: Defines the discriminator column used to differentiate subclass types.
- `@DiscriminatorValue`: Specifies the value stored in the discriminator column for each subclass.

### **2. Joined Table Strategy (Table per Subclass)**

In this strategy, each class in the hierarchy is mapped to its own table. Subclass tables have a primary key that also serves as a foreign key referencing the base class table.

#### **Advantages:**
- Normalized data with no null values.
- Clear separation of fields related to specific classes.

#### **Disadvantages:**
- Complex joins when querying subclasses.
- Slower performance compared to the single table strategy due to multiple joins.

#### **Example:**

```java
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Getters and Setters
}

@Entity
public class Car extends Vehicle {
    private int numberOfDoors;

    // Getters and Setters
}

@Entity
public class Bike extends Vehicle {
    private boolean hasPedals;

    // Getters and Setters
}
```

**Generated Tables:**

- `Vehicle` Table:

  | ID | NAME     |
  |----|----------|
  | 1  | Sedan    |
  | 2  | Mountain |

- `Car` Table:

  | ID | NUMBER_OF_DOORS |
  |----|-----------------|
  | 1  | 4               |

- `Bike` Table:

  | ID | HAS_PEDALS |
  |----|------------|
  | 2  | TRUE       |

**Annotation Used:**
- `@Inheritance(strategy = InheritanceType.JOINED)`: Specifies that subclasses are mapped to their own tables with foreign keys pointing to the parent table.

### **3. Table per Concrete Class (Table per Class)**

Each class in the hierarchy (including the base class) is mapped to its own table. Each table has columns for all fields, including those inherited from the superclass.

#### **Advantages:**
- No joins are required when querying, as all fields are present in each table.

#### **Disadvantages:**
- Data redundancy, as inherited fields are repeated across tables.
- Harder to maintain consistency.

#### **Example:**

```java
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Getters and Setters
}

@Entity
public class Car extends Vehicle {
    private int numberOfDoors;

    // Getters and Setters
}

@Entity
public class Bike extends Vehicle {
    private boolean hasPedals;

    // Getters and Setters
}
```

**Generated Tables:**

- `Car` Table:

  | ID | NAME  | NUMBER_OF_DOORS |
  |----|-------|-----------------|
  | 1  | Sedan | 4               |

- `Bike` Table:

  | ID | NAME     | HAS_PEDALS |
  |----|----------|------------|
  | 2  | Mountain | TRUE       |

**Annotation Used:**
- `@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)`: Each class in the hierarchy maps to its own table.

### **4. Mapped Superclass Strategy**

This strategy is used when the superclass is not an entity but provides common mappings for subclasses. The superclass itself does not have a table; its fields are inherited by subclasses.

#### **Advantages:**
- Only relevant for common attribute sharing.
- Clean separation between abstract superclasses and concrete subclasses.

#### **Disadvantages:**
- Cannot be queried directly since it’s not an entity.

#### **Example:**

```java
import jakarta.persistence.*;

@MappedSuperclass
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // Getters and Setters
}

@Entity
public class Car extends Vehicle {
    private int numberOfDoors;

    // Getters and Setters
}

@Entity
public class Bike extends Vehicle {
    private boolean hasPedals;

    // Getters and Setters
}
```

**Generated Tables:**

- `Car` Table:

  | ID | NAME  | NUMBER_OF_DOORS |
  |----|-------|-----------------|
  | 1  | Sedan | 4               |

- `Bike` Table:

  | ID | NAME     | HAS_PEDALS |
  |----|----------|------------|
  | 2  | Mountain | TRUE       |

**Annotation Used:**
- `@MappedSuperclass`: Indicates that this class’s fields are mapped to the entities inheriting from it, but it doesn’t have a separate table.

### **Choosing the Right Strategy**

- **Single Table Strategy**: Best for performance, but only if the schema can accommodate nullable columns.
- **Joined Table Strategy**: Ideal when data normalization and enforcing `NOT NULL` constraints are important.
- **Table per Concrete Class**: Suitable when subclass entities rarely share functionality or attributes.
- **Mapped Superclass**: Use when you only need to share attributes without making the superclass an entity.

Each strategy has its strengths and is suitable for different scenarios based on data structure, performance requirements, and maintainability considerations.