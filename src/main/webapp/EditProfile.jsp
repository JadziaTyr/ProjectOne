<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.revature.models.User" %>

<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Edit Profile</title>
	<link href="https://fonts.googleapis.com/css?family=Permanent+Marker" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="resources/css/style.css">
</head>

<body class="gothic-font">  

<div class="container">

<jsp:include page="resources/jsps/header.jsp">
    <jsp:param name="heading" value="PROFILE OF ${sessionScope.user.getFullName()}"/>
</jsp:include>

<div class = "row">
	<div class="col-md-3"></div>
	<form class = "col-md-4" action = "master.do" method = "POST">
		<input type = "text" class="form-control" name = "first_name" value = "<%=((User)session.getAttribute("user")).getFirstName()%>" required>
		<input type = "text" class="form-control" name = "last_name" value = "<%=((User)session.getAttribute("user")).getLastName()%>" required>
		<input type = "text" class="form-control" name = "username" value = "<%=((User)session.getAttribute("user")).getUserName()%>" required>
		<input type = "text" class="form-control" name = "password" value = "<%=((User)session.getAttribute("user")).getPassword()%>" required>
		<input type = "email" class="form-control" name = "email" value = "<%=((User)session.getAttribute("user")).getEmail()%>" required>
		<button class="btn btn-lg btn-primary col-md-12" type="submit" name = "saveEditButton">Save Changes</button>
	</form>
	<div class="col-md-5"></div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

</div> <!-- end container -->
</body>
</html>