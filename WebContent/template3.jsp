<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src = "veginere.js"> </script>
	<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
	<link rel="stylesheet" type="text/css" href="myStyle.css">
</head>

<body>
	<div id="nav-placeholder"></div>

	<script>
		$(function(){
			$("#nav-placeholder").load("navigation/nav.jsp");
		});
	</script>

	<form class='canvas'>

			<%
			
			ArrayList<String> list = (ArrayList<String>) request.getAttribute("data");
			
			for (int i = 0; i < list.size(); i = i + 4)
			{
				out.println("<div class='subcanvas'>");
				out.println("<p><h3>Task #" + list.get(i) + "</h3>");
				out.println("<p><h3>" + list.get(i + 1) + " was created at " + list.get(i + 2) + "</h3>");
				out.println("<p>" + list.get(i + 3));
				out.println("<p><p><a href=\"addTask.html\">Add task</a>");
				out.println("</div>");
			}
			
			%>
	</form>

<div id="bot-placeholder"></div>
<script>
		$(function(){
		$("#bot-placeholder").load("navigation/bot.jsp");
		});
</script>

</body>
</html> 