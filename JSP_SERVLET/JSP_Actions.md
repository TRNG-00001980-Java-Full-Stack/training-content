Actions in JSP (JavaServer Pages) are used to control the behavior of the JSP engine or to dynamically include resources and instantiate JavaBeans. They provide a way to work with Java components, manage the flow of control, and interact with external resources. JSP actions are written in XML syntax and enable seamless interaction between JSP pages and other Java components like beans and servlets.

Here are the main JSP actions and their uses, along with examples:

### 1. `<jsp:useBean>`
This action is used to instantiate a JavaBean or locate an existing one.

- **Attributes**:
  - `id`: The variable name by which the bean is referred.
  - `scope`: Specifies the scope (page, request, session, or application).
  - `class`: The fully qualified class name of the bean.
  - `type`: The class or interface type to which the bean reference will be cast.

**Example**:
```jsp
<jsp:useBean id="userBean" class="com.example.User" scope="session"/>
```
This will create or locate a `User` bean and store it in the session scope.

### 2. `<jsp:setProperty>`
This action is used to set properties of a JavaBean. It can either set a specific property or all properties.

- **Attributes**:
  - `name`: The name of the bean.
  - `property`: The property to set. Use `"*"` to set all properties that match request parameters.
  - `value`: The value to set (optional).

**Example**:
```jsp
<jsp:useBean id="userBean" class="com.example.User" scope="session"/>
<jsp:setProperty name="userBean" property="username" value="JohnDoe"/>
```
This sets the `username` property of the `userBean` to "JohnDoe".

**Setting all properties**:
```jsp
<jsp:useBean id="userBean" class="com.example.User" scope="session"/>
<jsp:setProperty name="userBean" property="*"/>
```
This will set all matching properties from the request parameters.

### 3. `<jsp:getProperty>`
This action retrieves a property from a JavaBean and outputs it to the page.

- **Attributes**:
  - `name`: The name of the bean.
  - `property`: The property to retrieve.

**Example**:
```jsp
<jsp:useBean id="userBean" class="com.example.User" scope="session"/>
<p>Username: <jsp:getProperty name="userBean" property="username"/></p>
```
This outputs the value of the `username` property of the `userBean`.

### 4. `<jsp:include>`
This action dynamically includes another JSP or resource (like HTML or a servlet) at runtime.

- **Attributes**:
  - `page`: The relative URL of the page or resource to include.
  - `flush`: Optional attribute to flush the buffer before including (default is `true`).

**Example**:
```jsp
<jsp:include page="header.jsp" />
```
This includes the `header.jsp` file dynamically during page rendering.

### 5. `<jsp:forward>`
This action forwards the request to another resource, such as a servlet or another JSP page. Once forwarded, the control never returns to the current page.

- **Attributes**:
  - `page`: The relative URL of the page or resource to forward to.

**Example**:
```jsp
<jsp:forward page="success.jsp" />
```
This forwards the request to `success.jsp`.

### 6. `<jsp:plugin>`
This action generates HTML code for an object or applet to be embedded in the browser. It is mainly used for including Java applets or objects that need to be rendered in the client.

- **Attributes**:
  - `type`: Specifies whether it's an "applet" or "bean".
  - `code`: The class or bean to load.
  - `width`, `height`: Specifies the dimensions of the applet or object.

**Example**:
```jsp
<jsp:plugin type="applet" code="MyApplet.class" width="300" height="300">
  <jsp:params>
    <jsp:param name="param1" value="value1"/>
  </jsp:params>
</jsp:plugin>
```
This embeds a Java applet in the JSP page.

### 7. `<jsp:param>`
This action is used within other actions like `<jsp:include>`, `<jsp:forward>`, or `<jsp:plugin>` to pass parameters.

- **Attributes**:
  - `name`: The name of the parameter.
  - `value`: The value of the parameter.

**Example**:
```jsp
<jsp:include page="header.jsp">
  <jsp:param name="title" value="My Page Title"/>
</jsp:include>
```
This includes the `header.jsp` page and passes the `title` parameter with the value `"My Page Title"`.

### Full Example:
```jsp
<jsp:useBean id="userBean" class="com.example.User" scope="session"/>
<jsp:setProperty name="userBean" property="*" />
<h1>Welcome, <jsp:getProperty name="userBean" property="username" /></h1>

<jsp:include page="menu.jsp">
  <jsp:param name="section" value="home"/>
</jsp:include>

<jsp:forward page="nextPage.jsp" />
```
This example demonstrates creating a bean, setting properties from the request, retrieving the `username`, including a menu, and forwarding to another page.

### Summary of JSP Actions:
- `<jsp:useBean>`: Instantiate or locate a JavaBean.
- `<jsp:setProperty>`: Set bean properties.
- `<jsp:getProperty>`: Get bean properties.
- `<jsp:include>`: Include another resource (JSP, HTML).
- `<jsp:forward>`: Forward the request to another resource.
- `<jsp:plugin>`: Generate HTML to include Java applets or objects.
- `<jsp:param>`: Pass parameters to included or forwarded resources.

These JSP actions allow you to work with JavaBeans, include dynamic content, and control the flow of your JSP application effectively.