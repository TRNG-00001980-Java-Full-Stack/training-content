Distributed tracing in microservices is crucial for observing and monitoring the flow of requests across different services, helping to identify bottlenecks, reduce latency, and debug issues effectively. Spring Boot, being a popular framework for microservices, provides support for distributed tracing, and with the deprecation of **Spring Cloud Sleuth** in favor of **Micrometer Tracing**, it's important to learn how to use the latest tools.

In this detailed tutorial, we'll walk through the steps to implement distributed tracing in a Spring Boot microservices architecture using **Micrometer Tracing** with **OpenTelemetry** and a tracing backend like **Jaeger** or **Zipkin**.

### Table of Contents
1. [What is Distributed Tracing?](#what-is-distributed-tracing)
2. [Prerequisites](#prerequisites)
3. [Overview of Tools](#overview-of-tools)
4. [Step 1: Set up Spring Boot Microservice](#step-1-set-up-spring-boot-microservice)
5. [Step 2: Add Dependencies](#step-2-add-dependencies)
6. [Step 3: Configure Micrometer Tracing](#step-3-configure-micrometer-tracing)
7. [Step 4: Set Up a Tracing Backend](#step-4-set-up-a-tracing-backend)
8. [Step 5: Run and Observe Traces](#step-5-run-and-observe-traces)
9. [Step 6: Implement Tracing Across Multiple Microservices](#step-6-implement-tracing-across-multiple-microservices)

---

### What is Distributed Tracing?

Distributed tracing is a method of tracking and observing requests that traverse across multiple microservices. Each request is given a unique trace ID, and every action or event within that request is associated with spans, which help pinpoint the journey and performance bottlenecks across different services.

---

### Prerequisites

1. **Java 17 or higher** (recommended)
2. **Spring Boot 3.x** (which supports Micrometer Tracing)
3. **Gradle or Maven** build tool
4. **Docker** (for running the tracing backend like Jaeger or Zipkin)
5. Basic understanding of microservices architecture.

---

### Overview of Tools

- **Micrometer Tracing**: A library for application performance monitoring (APM) in Spring Boot, which integrates with OpenTelemetry.
- **OpenTelemetry**: A set of APIs, libraries, and instrumentation for distributed tracing and metrics collection.
- **Jaeger** or **Zipkin**: Popular tracing backends that visualize distributed traces.

---

### Step 1: Set up Spring Boot Microservice

If you already have a Spring Boot microservice, skip this step. If not, create a new Spring Boot project using Spring Initializr:

1. Go to [Spring Initializr](https://start.spring.io/).
2. Choose the following:
   - Project: **Maven** or **Gradle**
   - Spring Boot Version: **3.x.x**
   - Dependencies: **Spring Web**, **Spring Boot Actuator**, **Micrometer Tracing**
3. Generate and download the project.
4. Import the project into your preferred IDE (e.g., IntelliJ, Eclipse).

---

### Step 2: Add Dependencies

If you're working with Maven, ensure you have the following dependencies in your `pom.xml`. For Gradle, use equivalent `build.gradle` entries.

#### Maven Configuration

Add the following dependencies for **Micrometer Tracing** and **OpenTelemetry**.

```xml
<dependencies>
    <!-- Spring Boot Starter for Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Micrometer Tracing -->
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-tracing-bridge-otel</artifactId>
    </dependency>

    <!-- OpenTelemetry SDK -->
    <dependency>
        <groupId>io.opentelemetry</groupId>
        <artifactId>opentelemetry-sdk</artifactId>
        <version>1.26.0</version> <!-- Use latest stable version -->
    </dependency>

    <!-- OpenTelemetry Exporter for Jaeger -->
    <dependency>
        <groupId>io.opentelemetry</groupId>
        <artifactId>opentelemetry-exporter-jaeger</artifactId>
        <version>1.26.0</version>
    </dependency>

    <!-- Micrometer Prometheus support (Optional for Prometheus metrics) -->
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
</dependencies>
```

#### Gradle Configuration

For Gradle, add the following dependencies to `build.gradle`:

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'io.micrometer:micrometer-tracing-bridge-otel'
    implementation 'io.opentelemetry:opentelemetry-sdk:1.26.0'
    implementation 'io.opentelemetry:opentelemetry-exporter-jaeger:1.26.0'
    implementation 'io.micrometer:micrometer-registry-prometheus' // optional
}
```

---

### Step 3: Configure Micrometer Tracing

In your `application.properties` or `application.yml`, add the following configuration for OpenTelemetry and Jaeger:

#### application.properties

```properties
# Enable tracing
management.tracing.enabled=true

# OpenTelemetry exporter (using Jaeger)
otel.traces.exporter=jaeger

# Jaeger configuration
otel.exporter.jaeger.endpoint=http://localhost:14250
otel.exporter.jaeger.service.name=my-springboot-app

# Optional: Enable Micrometer with Prometheus
management.metrics.export.prometheus.enabled=true
```

This configuration enables tracing via OpenTelemetry and exports traces to Jaeger running on your local machine.

---

### Step 4: Set Up a Tracing Backend

To visualize traces, we need a tracing backend. You can use Jaeger or Zipkin. Here we’ll use **Jaeger**.

#### Running Jaeger via Docker

Run Jaeger in Docker with the following command:

```bash
docker run -d --name jaeger \
  -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14268:14268 \
  -p 14250:14250 \
  -p 9411:9411 \
  jaegertracing/all-in-one:1.31
```

This command sets up a Jaeger instance running on your local machine, and you can access the Jaeger UI at `http://localhost:16686`.

---

### Step 5: Run and Observe Traces

1. **Start the Spring Boot application**:
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Make some requests** to your application. For example:
   ```bash
   curl http://localhost:8080/your-endpoint
   ```

3. **Check Jaeger**:
   - Go to `http://localhost:16686` to open the Jaeger UI.
   - Search for traces related to your service (`my-springboot-app`).

You should see traces of your requests, with spans indicating how the request propagated through the application.

---

### Step 6: Implement Tracing Across Multiple Microservices

To track a request across multiple microservices, you can repeat the steps for each microservice, ensuring that each has Micrometer Tracing and OpenTelemetry set up.

1. **Add dependencies** for Micrometer and OpenTelemetry to each microservice.
2. **Ensure trace IDs are propagated** between services:
   - Spring Boot will automatically propagate trace IDs via HTTP headers when calling other services if Micrometer Tracing is configured.

For example, when one microservice calls another (using RestTemplate, WebClient, or Feign), the trace context will automatically propagate across the services.

3. **Observe distributed traces in Jaeger**:
   - Once requests traverse multiple services, Jaeger will show the complete trace, including spans from all services involved in fulfilling the request.

---

### Conclusion

By following this tutorial, you’ve set up **Micrometer Tracing** in a Spring Boot microservices architecture using **OpenTelemetry** and **Jaeger**. You can now trace requests across multiple services, providing visibility into your distributed system’s behavior and helping you to identify and debug performance bottlenecks.

This setup is also flexible and can be extended with other tracing backends like **Zipkin** or **Prometheus** for monitoring.