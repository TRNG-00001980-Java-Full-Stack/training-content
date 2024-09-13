<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>RevShop	</title>
</head>
<body>
<h1>Register</h1>

<form action="/RevShop/api/v1/users" method="POST">
	<label>Username</label>
	<input type="text" name="username" ><br>
	<label>Password</label>
	<input type="password" name="password"><br>
	<label>email</label>
	<input type = "email" name ="email"><br>
	<input type="submit" value="Register">
</form>

</body>
</html>