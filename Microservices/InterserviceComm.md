In a microservices architecture, inter-service communication can be established using REST APIs. In this case, `CartService` needs to communicate with `ProductService` to fetch product information. There are two popular ways to do this in Spring Boot:

1. **Using `RestTemplate`** (Legacy approach but still widely used)
2. **Using `OkHttp`** (More lightweight and customizable HTTP client)

Here, I'll provide examples for both approaches.

### 1. **Using `RestTemplate`**

**Step 1: Setup RestTemplate in your `CartService`**

In `CartService`, you need to define a `RestTemplate` bean. Spring will automatically inject this bean where it's needed.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

**Step 2: Call `ProductService` from `CartService`**

Now, you can use the `RestTemplate` to communicate with `ProductService`. Assume you have a method in `ProductService` to get product details by `productId`.

In `CartService`:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService {

    @Autowired
    private RestTemplate restTemplate;

    private final String PRODUCT_SERVICE_URL = "http://localhost:8081/products"; // URL of ProductService

    public ProductDTO getProductDetails(String productId) {
        String url = PRODUCT_SERVICE_URL + "/" + productId;
        return restTemplate.getForObject(url, ProductDTO.class);
    }
}
```

Here, `ProductDTO` is the data transfer object that represents the product details.

**Step 3: ProductDTO class**

Define the DTO to hold product details.

```java
public class ProductDTO {
    private String id;
    private String name;
    private double price;

    // Getters and Setters
}
```

**Step 4: ProductService Controller (For completeness)**

Assuming `ProductService` has an endpoint like this:

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/products/{productId}")
    public ProductDTO getProductById(@PathVariable String productId) {
        // Logic to get the product details by productId
        ProductDTO product = new ProductDTO();
        product.setId(productId);
        product.setName("Sample Product");
        product.setPrice(99.99);
        return product;
    }
}
```

---

### 2. **Using `OkHttp`**

If you prefer to use `OkHttp` instead of `RestTemplate`, you can follow these steps.

**Step 1: Add `OkHttp` dependency in `pom.xml`**

Add the following dependency for OkHttp:

```xml
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.9.3</version>
</dependency>
```

**Step 2: Setup OkHttpClient in your `CartService`**

You can configure `OkHttpClient` as a bean or directly use it in your service class.

```java
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CartService {

    private OkHttpClient client = new OkHttpClient();

    private final String PRODUCT_SERVICE_URL = "http://localhost:8081/products";

    public ProductDTO getProductDetails(String productId) throws IOException {
        String url = PRODUCT_SERVICE_URL + "/" + productId;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Assuming the response body is JSON and deserializing it into ProductDTO
            return new ObjectMapper().readValue(response.body().string(), ProductDTO.class);
        }
    }
}
```

**Step 3: Handling Response with OkHttp**

In this example, I use the `ObjectMapper` from Jackson to deserialize the JSON response to a `ProductDTO` object. Make sure you have Jackson dependency in your project:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.5</version>
</dependency>
```

### Example Project Structure

- **CartService**: Calls `ProductService` to fetch product information.
- **ProductService**: Provides product details.

---

### 3. **Configuration with Service Discovery (Eureka)**
In a microservices environment, you can use **Spring Cloud Netflix Eureka** for service discovery instead of hardcoding URLs. Here’s a quick way to set it up:

- Add `@EnableEurekaClient` to your main classes.
- Instead of `localhost`, use the service name (e.g., `PRODUCT-SERVICE`).
- Example for `RestTemplate`:

```java
@Autowired
private RestTemplate restTemplate;

private final String PRODUCT_SERVICE_URL = "http://PRODUCT-SERVICE/products"; // Service name

public ProductDTO getProductDetails(String productId) {
    String url = PRODUCT_SERVICE_URL + "/" + productId;
    return restTemplate.getForObject(url, ProductDTO.class);
}
```

This way, `PRODUCT-SERVICE` will be resolved by the Eureka service registry.
