In this tutorial, we will dive into **One-to-Many**, **Many-to-One**, and **Many-to-Many** relationships in **Hibernate with Spring Boot**. We will also explore how to use the **@JoinColumn** annotation to specify foreign keys.

### 1. **Understanding Relationships**

- **One-to-Many**: A single entity is related to many instances of another entity. For example, a **Department** can have many **Employees**.
  
- **Many-to-One**: This is the inverse of One-to-Many. An **Employee** belongs to a single **Department**.

- **Many-to-Many**: Many instances of an entity can relate to many instances of another entity. For example, a **Student** can enroll in many **Courses**, and a **Course** can have many **Students**.

### 2. **Project Setup**

Ensure your project has the required dependencies (similar to the previous tutorial):
- Spring Boot Starter Data JPA
- MySQL Connector
- Spring Boot Starter Web

### 3. **One-to-Many and Many-to-One Relationship Example**

#### Scenario:
- A **Department** has multiple **Employees** (One-to-Many).
- An **Employee** belongs to a single **Department** (Many-to-One).

#### 1. **Department Entity**

```java
package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // One department can have many employees
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;

    // Constructors, getters, and setters
    public Department() {}

    public Department(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
```

#### 2. **Employee Entity**

```java
package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    // Many employees belong to one department
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Constructors, getters, and setters
    public Employee() {}

    public Employee(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
```

- `@OneToMany`: The `mappedBy` attribute refers to the `department` property in the `Employee` class. This tells Hibernate that the **Employee** entity will manage the foreign key column.
- `@ManyToOne`: The `@JoinColumn(name = "department_id")` annotation specifies the foreign key column that will link the **Employee** table to the **Department** table.

#### 3. **Repositories**

```java
package com.example.demo.repository;

import com.example.demo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
```

```java
package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
```

#### 4. **Service Layer**

```java
package com.example.demo.service;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
```

#### 5. **Controller**

```java
package com.example.demo.controller;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/departments")
    public Department createDepartment(@RequestBody Department department) {
        return organizationService.saveDepartment(department);
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return organizationService.saveEmployee(employee);
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return organizationService.getAllEmployees();
    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return organizationService.getAllDepartments();
    }
}
```

### 4. **Many-to-Many Relationship Example**

#### Scenario:
- A **Student** can enroll in multiple **Courses**.
- A **Course** can have multiple **Students** enrolled.

#### 1. **Student Entity**

```java
package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // Many students can enroll in many courses
    @ManyToMany
    @JoinTable(
      name = "students_courses", 
      joinColumns = @JoinColumn(name = "student_id"), 
      inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

    // Constructors, getters, and setters
    public Student() {}

    public Student(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
```

#### 2. **Course Entity**

```java
package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // Many courses can have many students
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    // Constructors, getters, and setters
    public Course() {}

    public Course(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
```
