<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
	<link rel = "stylesheet" type = "text/css" href = "myStyle.css">
</head>

<body>
	<div class = "topnav">
	
	<a href="index.html">Home</a>
	
	<%
	 Cookie[] cookies = null;
	 cookies = request.getCookies();
	 String name = null;
	 
	 if (cookies != null)
	 {
		if (cookies.length == 2)
		{
	 		name = cookies[1].getValue();
		}
	 }
	
	%>
	<%if (name == null) {%>
		<div class="auth">
			<a href="auth.html">Log In</a>
		</div>
	<% 
	} else { %>
		<form action="Authorize" method="POST">
			<div class="dropdown">
				<button class="dropbtn" ><%out.print(name);%></button>
				<div class="dropdown-content">
					<a href="Profile">Profile</a>
					<a href="GetTask">My tasks</a>
					<a href="Phonebook">My Phone Book</a>
					<a href="LogOut">Log out</a>	
				</div>
			</div>
		</form>
	<%}
		name = null;
	%>
	
	</div>
</body>	
</html>