<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ page language="java"  import="java.util.Date" %>
<%@ page import="models.User" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ include file="nav.jsp" %>

<h2>USER LIST</h2>
<p>Phno:${param.phno}</p>
<p><%= new Date() %></p>
<h3>
<c:out value="${topic}" />
<c:set var="appname" value="RevShop"/>
<c:out value="${appname }" />
</h3>

<table>
<thead>
	<tr>
		<th>id</th>
		<th>name</th>
		<th>email</th>
		<th>password</th>
	</tr>
</thead>
<tbody>
	<c:forEach var="user" items="${userlist}" >
		<tr>
			<td>${user.id }</td>
			<c:if test="${user.username == 'Admin'}">
						<td><b>${user.username }</b></td>
			
			</c:if>
			
			
			<td>${user.username }
			<c:choose>
				<c:when test="${user.username == 'ram' }">
				
					<b>R</b>
				</c:when>
				<c:otherwise>
					<i>R</i>
				</c:otherwise>
			</c:choose>
			
			</td>
			<td>${user.email }</td>
			<td>${user.password}</td>
			<td><button onclick="deleteUser(${user.id })">DELETE</button></td>
		</tr>
	</c:forEach>
</tbody>
</table>
<div>
<jsp:useBean id="user" class="models.User" scope="session" />

<jsp:setProperty name="user" property="username" value="Arul"/>
<jsp:setProperty name="user" property="email" value="arul131@qwe.com"/>
<jsp:setProperty name="user" property="password" value="123qwe"/>

<p>
<jsp:getProperty name="user" property="username" />
</p>



</div>

<%@ include file="footer.jsp" %>
</body>
<script>
function deleteUser(userid){
	fetch('users?id='+userid, {method:'DELETE'}).then(
				response => console.log(response)
			)
			.catch(error => console.log("error:::"+error))
}
</script>
</html>