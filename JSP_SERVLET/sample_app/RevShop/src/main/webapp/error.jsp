<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true" import="java.util.logging.*"%>
<!DOCTYPE html>

<%
	Exception ex = (Exception)request.getAttribute("Exception");
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>An error occured</h2>
<p>Sorry, Something went wrong</p>
<blockquote>
Exception: <%= ex.getClass().getName() %><br/>
Message: <%= ex.getMessage() %>
</blockquote>
</body>
</html>