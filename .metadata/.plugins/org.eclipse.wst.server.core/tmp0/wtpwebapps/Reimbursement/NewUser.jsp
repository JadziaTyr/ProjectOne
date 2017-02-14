<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.revature.models.User"%>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Profile</title>
<link href="https://fonts.googleapis.com/css?family=Permanent+Marker"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<%@ include file="resources/jsps/script_libraries.jsp"%>

</head>

<body class="gothic-font">

	<div class="container">

		<jsp:include page="resources/jsps/header.jsp">
			<jsp:param name="heading" value="Create New User" />
		</jsp:include>

		<div class="col-md-4 col-md-offset-4">

			<div class="boxshadow blue center-block text-center">

					<h2 id="message" class="form-signin-heading">Create User Here</h2>

					<input id="first_name" type="text" class="form-control" name="first_name"
						placeholder="First Name" required> <input id="last_name" type="text"
						class="form-control" name="last_name" placeholder="Last Name"
						required> <input id="email" type="email" class="form-control"
						name="email" placeholder="Email" required>
				<select id="roles" name="roles" class="form-control">
					<c:forEach items="${userRoles}" var="role">
						<option value="${role.getId()}">${role.getTitle()}</option>
					</c:forEach>
				</select>
					<button id = "saveUserButton" class="btn btn-lg btn-warning" type="submit"
						name="saveUserButton">Save New User</button>
			</div>
		</div>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script type="text/javascript" src="resources/js/myNewUserSource.js"></script>

	</div>
	<!-- end container -->
</body>
</html>