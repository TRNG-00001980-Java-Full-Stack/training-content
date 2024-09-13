<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
session.setAttribute("att1","xyz");

%>

<p> ${cookie.username.value } </p>
<p> ${header['head01']} </p>
<form action="/RevShop/MailServlet" method="POST">
<label>TO:</label>
<input type="email" name="to" required> <br>

<label>Subject:</label>
<input type="text" name="subject" required> <br>

<label>Message</label>
<textarea name="message" required cols="60" row="10"></textarea> <br>

<input type="submit" value="Send">
</form>

</body>
</html>