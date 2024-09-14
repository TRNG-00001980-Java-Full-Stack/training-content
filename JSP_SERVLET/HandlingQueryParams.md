In servlets, **query parameters** are part of the URL and are used to pass data to a servlet. These parameters are typically found in **GET** requests, though they can also be included in **POST** requests via the request body. Query parameters are key-value pairs that come after the `?` symbol in a URL, separated by `&`.

### Example URL with Query Parameters:
```
http://example.com/servlet?name=John&age=30
```

In the above URL:
- `name` is a query parameter with the value `John`.
- `age` is a query parameter with the value `30`.

### **Retrieving Query Parameters in a Servlet**
To retrieve query parameters in a servlet, you can use the `HttpServletRequest` object provided by the servlet container. The `HttpServletRequest` interface provides several methods to access query parameters:

- `getParameter(String name)`: Returns the value of a single query parameter.
- `getParameterValues(String name)`: Returns an array of values for a multi-value parameter.
- `getParameterNames()`: Returns an `Enumeration` of all parameter names.
- `getQueryString()`: Returns the full query string.

### **Example Servlet Handling Query Parameters:**

```java
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryParamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Setting response content type
        response.setContentType("text/html");

        // Get a single query parameter
        String name = request.getParameter("name");
        String age = request.getParameter("age");

        // Get multiple values (e.g., for checkboxes)
        String[] hobbies = request.getParameterValues("hobby");

        // Print the response
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h2>Query Parameters:</h2>");
        response.getWriter().println("<p>Name: " + name + "</p>");
        response.getWriter().println("<p>Age: " + age + "</p>");

        if (hobbies != null) {
            response.getWriter().println("<p>Hobbies: ");
            for (String hobby : hobbies) {
                response.getWriter().println(hobby + " ");
            }
            response.getWriter().println("</p>");
        } else {
            response.getWriter().println("<p>No hobbies selected</p>");
        }

        response.getWriter().println("</body></html>");
    }
}
```

### **Steps to Handle Query Parameters:**

1. **Retrieve Single Parameter:**
   ```java
   String name = request.getParameter("name");
   ```
   This method returns the value of the query parameter with the key `name`. If the parameter is not found, it returns `null`.

2. **Retrieve Multiple Values:**
   ```java
   String[] hobbies = request.getParameterValues("hobby");
   ```
   For a parameter like checkboxes that can have multiple values, `getParameterValues()` returns an array of strings. For example, if a user selects multiple hobbies from a list, this method captures all the selected values.

3. **Access All Parameters:**
   ```java
   Enumeration<String> parameterNames = request.getParameterNames();
   while (parameterNames.hasMoreElements()) {
       String paramName = parameterNames.nextElement();
       String paramValue = request.getParameter(paramName);
       // Do something with paramName and paramValue
   }
   ```
   This code will iterate over all parameters and print their names and values.

4. **Get Full Query String:**
   ```java
   String queryString = request.getQueryString();
   ```
   This method returns the full query string, which is everything after the `?` in the URL (e.g., `name=John&age=30`).

---

### **Example URL for the Servlet:**
Assuming the servlet is mapped to `/QueryParamServlet`, here’s how you can make a request with query parameters:

```
http://localhost:8080/QueryParamServlet?name=Alice&age=25&hobby=reading&hobby=cycling
```

- `name=Alice`
- `age=25`
- `hobby=reading`
- `hobby=cycling` (multiple hobbies passed using the same parameter name)

The servlet will output:
```html
<html>
  <body>
    <h2>Query Parameters:</h2>
    <p>Name: Alice</p>
    <p>Age: 25</p>
    <p>Hobbies: reading cycling </p>
  </body>
</html>
```

---

### **Handling Query Parameters in POST Requests**
In **POST** requests, query parameters are generally passed in the request body, not the URL. However, the same `getParameter()` and `getParameterValues()` methods are used to retrieve them.

Here’s how to handle query parameters in a **POST** request:

```java
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String name = request.getParameter("name");
    String age = request.getParameter("age");

    response.getWriter().println("<p>Name: " + name + "</p>");
    response.getWriter().println("<p>Age: " + age + "</p>");
}
```

When submitting a POST request via an HTML form, query parameters can be sent in the request body.