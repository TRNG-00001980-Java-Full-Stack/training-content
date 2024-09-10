Here’s a simple example of how to add products using JSP and Servlets in a Java web application. This example will allow a user to add products through a form and display the product list on another page.

### Project Setup

1. **JSP page for adding a product** (`addProduct.jsp`)
2. **Servlet for handling the form submission** (`AddProductServlet`)
3. **JSP page for displaying the product list** (`productList.jsp`)
4. **Product class** to represent a product

### 1. Product Class

Create a simple `Product` class to hold product information.

```java
public class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
```

### 2. JSP Form for Adding Products (`addProduct.jsp`)

This form allows users to add a product by entering its name and price.

```jsp
<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
    <h2>Add Product</h2>
    <form action="addProduct" method="post">
        <label for="name">Product Name:</label>
        <input type="text" id="name" name="name" required><br><br>
        <label for="price">Product Price:</label>
        <input type="number" step="0.01" id="price" name="price" required><br><br>
        <input type="submit" value="Add Product">
    </form>
    <br>
    <a href="productList.jsp">View Product List</a>
</body>
</html>
```

### 3. Servlet for Handling Product Submission (`AddProductServlet.java`)

This servlet handles the form submission and adds the product to a shared list in the session.

```java
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));

        // Create product object
        Product product = new Product(name, price);

        // Get the session and the product list
        HttpSession session = request.getSession();
        List<Product> productList = (List<Product>) session.getAttribute("products");

        // If product list is null, initialize it
        if (productList == null) {
            productList = new ArrayList<>();
        }

        // Add the new product to the list
        productList.add(product);

        // Store the updated product list in the session
        session.setAttribute("products", productList);

        // Redirect to the product list page
        response.sendRedirect("productList.jsp");
    }
}
```

### 4. JSP Page for Displaying the Product List (`productList.jsp`)

This page displays all the products that have been added.

```jsp
<%@ page import="java.util.List" %>
<%@ page import="your.package.Product" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
</head>
<body>
    <h2>Product List</h2>
    <%
        List<Product> products = (List<Product>) session.getAttribute("products");

        if (products != null && !products.isEmpty()) {
    %>
        <table border="1">
            <tr>
                <th>Product Name</th>
                <th>Product Price</th>
            </tr>
            <%
                for (Product product : products) {
            %>
                <tr>
                    <td><%= product.getName() %></td>
                    <td><%= product.getPrice() %></td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <p>No products added yet.</p>
    <%
        }
    %>
    <br>
    <a href="addProduct.jsp">Add another product</a>
</body>
</html>
```

### 5. `web.xml` Configuration (Optional for Older Servlet Containers)

If you're not using annotations like `@WebServlet`, you will need to configure the servlet in the `web.xml`:

```xml
<servlet>
    <servlet-name>AddProductServlet</servlet-name>
    <servlet-class>your.package.AddProductServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>AddProductServlet</servlet-name>
    <url-pattern>/addProduct</url-pattern>
</servlet-mapping>
```

### Project Flow

1. The user accesses `addProduct.jsp` and submits the form.
2. The `AddProductServlet` processes the form data, adds the product to a list stored in the session, and redirects to the `productList.jsp`.
3. The `productList.jsp` page displays the products stored in the session, showing the current list of added products.

### Running the Example

- Ensure you have a servlet container like **Apache Tomcat** to run this example.
- Place the JSP files in the `webapp` folder and the servlet in the appropriate package.
- Access the application using your browser (`http://localhost:8080/yourapp/addProduct.jsp`).

This example demonstrates how to use JSP and Servlets to add and display products in a web application.