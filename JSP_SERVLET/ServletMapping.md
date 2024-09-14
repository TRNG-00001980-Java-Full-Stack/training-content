Servlet mapping is a crucial concept in Java web development, where servlets are mapped to specific URL patterns. It defines which URLs will be handled by a particular servlet, allowing the web server to route incoming requests to the correct servlet.

There are three primary ways to configure servlet mapping:
1. **URL Patterns in `web.xml` (Declarative Approach)**
2. **Annotations (Programmatic Approach)**
3. **Wildcard and Prefix Mappings**

### 1. **URL Patterns in `web.xml` (Declarative Approach)**
In the traditional approach, servlet mappings are configured in the `web.xml` file located under `WEB-INF/`.

#### Example: `web.xml`
```xml
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    
    <!-- Servlet declaration -->
    <servlet>
        <servlet-name>helloServlet</servlet-name>
        <servlet-class>com.example.HelloServlet</servlet-class>
    </servlet>

    <!-- Servlet mapping -->
    <servlet-mapping>
        <servlet-name>helloServlet</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>

</web-app>
```
Here, the servlet `HelloServlet` is mapped to the URL pattern `/hello`. Any request to `http://localhost:8080/contextPath/hello` will be handled by this servlet.

### 2. **Annotations (Programmatic Approach)**
With Servlet 3.0 and later versions, you can use annotations directly in your Java class to map URLs to servlets.

#### Example: Servlet with `@WebServlet`
```java
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().write("Hello from Servlet!");
    }
}
```
This servlet is mapped to `/hello` through the `@WebServlet("/hello")` annotation. This eliminates the need for a `web.xml` entry.

### 3. **Wildcard and Prefix Mappings**

- **Exact Match:** Maps a specific URL.  
  Example: `/hello`
  
- **Wildcard Suffix (`*.extension`):** Maps URLs with a specific extension.  
  Example: `*.do` will match URLs like `/process.do`.

- **Path Prefix (`/prefix/*`):** Matches any URL that starts with the specified path.  
  Example: `/admin/*` will match URLs like `/admin/home`, `/admin/login`.

#### Example: Wildcard and Prefix Mapping in `web.xml`
```xml
<servlet-mapping>
    <servlet-name>adminServlet</servlet-name>
    <url-pattern>/admin/*</url-pattern>
</servlet-mapping>
<servlet-mapping>
    <servlet-name>fileServlet</servlet-name>
    <url-pattern>*.do</url-pattern>
</servlet-mapping>
```
- `/admin/*` will forward requests like `/admin/home`, `/admin/profile` to `adminServlet`.
- `*.do` will forward requests like `/process.do`, `/validate.do` to `fileServlet`.

### Special URL Patterns:
- **Default Servlet:** If no specific servlet matches, a default servlet can be configured to handle requests.
- **Root Context (`/`):** Maps all requests to the servlet.
  
#### Example: Default Mapping
```xml
<servlet-mapping>
    <servlet-name>defaultServlet</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```
This catches all requests that aren't explicitly mapped elsewhere.

### Use Cases for Servlet Mapping:
- **Authentication:** Mapping servlets to specific security-related paths like `/login`.
- **File Processing:** Using file extension patterns (`*.xml`, `*.pdf`) for handling file downloads or uploads.
- **Dynamic Content:** Prefix-based mapping for handling admin or user sections (`/user/*`, `/admin/*`).

By combining these mapping techniques, you can configure servlets to respond to a wide variety of URLs, making your application flexible and scalable.