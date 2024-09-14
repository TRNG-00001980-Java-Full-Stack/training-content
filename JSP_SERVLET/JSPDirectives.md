Directives in JSP (JavaServer Pages) are special instructions given to the JSP engine to control how the JSP page is processed. These directives are usually placed at the top of the JSP page and provide global-level settings for the entire page.

There are three main types of directives in JSP:
1. **Page Directive (`<%@ page %>`)**
2. **Include Directive (`<%@ include %>`)**
3. **Taglib Directive (`<%@ taglib %>`)**

Let’s dive into each of these in detail with examples.

---

### 1. **Page Directive (`<%@ page %>`)**

The **page directive** provides information about the JSP page itself and affects the overall structure of the servlet generated from the JSP.

#### Syntax:
```jsp
<%@ page attribute="value" %>
```

#### Common Attributes of Page Directive:
- **`language`**: Specifies the scripting language used in the JSP. The default is `Java`.
  ```jsp
  <%@ page language="java" %>
  ```
  
- **`extends`**: Specifies a superclass that the generated servlet should extend.
  ```jsp
  <%@ page extends="com.example.MyServlet" %>
  ```

- **`import`**: Imports Java classes or entire packages into the JSP page.
  ```jsp
  <%@ page import="java.util.*, java.io.*" %>
  ```

- **`contentType`**: Specifies the MIME type of the response, such as `text/html`, `text/xml`, or `application/json`. It often includes the character encoding.
  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" %>
  ```

- **`session`**: Determines whether the JSP page will automatically participate in an HTTP session. Default is `true`.
  ```jsp
  <%@ page session="false" %>
  ```

- **`buffer`**: Sets the buffer size for the response. Default is `8kb`.
  ```jsp
  <%@ page buffer="16kb" %>
  ```

- **`autoFlush`**: Specifies whether the buffer should be flushed automatically when full. Default is `true`.
  ```jsp
  <%@ page autoFlush="false" %>
  ```

- **`errorPage`**: Specifies a URL to another JSP page that will handle exceptions.
  ```jsp
  <%@ page errorPage="error.jsp" %>
  ```

- **`isErrorPage`**: Indicates if the current page is an error page.
  ```jsp
  <%@ page isErrorPage="true" %>
  ```

- **`isELIgnored`**: Determines if Expression Language (EL) expressions should be ignored in the page. Default is `false`.
  ```jsp
  <%@ page isELIgnored="true" %>
  ```

#### Example of `page` Directive:
```jsp
<%@ page language="java" contentType="text/html;charset=UTF-8" import="java.util.*, java.io.*" session="true" %>
<html>
  <head><title>Page Directive Example</title></head>
  <body>
    <h1>Welcome to JSP Page Directive Example</h1>
  </body>
</html>
```

---

### 2. **Include Directive (`<%@ include %>`)**

The **include directive** is used to include the content of another JSP file at the translation (compile) time. This is different from the `<jsp:include>` action, which happens at runtime.

#### Syntax:
```jsp
<%@ include file="relativeURL" %>
```

#### Key Points:
- It is a static inclusion, meaning the content of the included file is merged with the current JSP during the translation phase.
- Changes to the included file will not be reflected unless the JSP page is recompiled.

#### Example of `include` Directive:
```jsp
<%@ include file="header.jsp" %>
<html>
  <body>
    <h1>Welcome to the Main Page</h1>
    <p>This is the content of the main page.</p>
  </body>
</html>
```

In this example, the content of `header.jsp` will be included in the main JSP page at compile time.

---

### 3. **Taglib Directive (`<%@ taglib %>`)**

The **taglib directive** is used to declare a custom tag library in a JSP page. Tag libraries allow you to use custom tags defined in external libraries, making the JSP more powerful and reusable.

#### Syntax:
```jsp
<%@ taglib uri="tagLibraryURI" prefix="tagPrefix" %>
```

#### Attributes:
- **`uri`**: Defines the unique identifier of the tag library.
- **`prefix`**: Specifies the prefix that will be used to invoke the custom tags from the tag library.

#### Example of `taglib` Directive:
Let's say you want to use the **JSTL** (JavaServer Pages Standard Tag Library), which is a common tag library for JSP pages.

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
    <h1>JSTL Example</h1>
    <c:if test="${user != null}">
      <p>Welcome, ${user.name}</p>
    </c:if>
  </body>
</html>
```

In this example:
- The `c` prefix is associated with the JSTL core library.
- The custom tag `<c:if>` is used to conditionally display content.

---

### Summary of JSP Directives:
- **Page Directive**: Configures global settings for the JSP page like imports, session handling, and error pages.
- **Include Directive**: Includes static resources (such as other JSP pages or HTML files) at translation time.
- **Taglib Directive**: Declares custom tag libraries like JSTL, making it easier to use reusable custom tags in JSP pages.

These directives give you control over how your JSP page behaves and interacts with other resources, allowing you to structure your JSP pages more effectively and efficiently.