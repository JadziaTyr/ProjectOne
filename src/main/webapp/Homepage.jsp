<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.revature.models.User" %>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Homepage</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<%@ include file="resources/jsps/script_libraries.jsp" %>  
<link href="https://fonts.googleapis.com/css?family=Gochi+Hand" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">

</head>

<body class="gothic-font">  

<div class="container">

<jsp:include page="resources/jsps/header.jsp">
    <jsp:param name="heading" value="Welcome ${sessionScope.user.getFullName()}"/>
</jsp:include>

<div class = "row">
	<div class="col-md-4"></div>
	<form class = "col-md-4">
		<div class="form-control"><%=((User)session.getAttribute("user")).getFirstName()%></div>
		<div class="form-control"><%=((User)session.getAttribute("user")).getLastName()%></div>
		<div class="form-control"><%=((User)session.getAttribute("user")).getUserName()%></div>
		<div class="form-control"><%=((User)session.getAttribute("user")).getPassword()%></div>
		<div class="form-control"><%=((User)session.getAttribute("user")).getEmail()%></div>
	</form>
	<div class="col-md-4"></div>
</div>

</div> <!-- end container -->

</body>
</html>