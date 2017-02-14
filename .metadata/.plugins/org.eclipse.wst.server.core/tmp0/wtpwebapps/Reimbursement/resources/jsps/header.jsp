<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.revature.models.User"%>
<%@ page import="com.revature.services.Logic"%>
<%@ include file="dropdown_libraries.jsp" %>  

<!DOCTYPE>
<link href="https://fonts.googleapis.com/css?family=Gochi+Hand"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link href="https://fonts.googleapis.com/css?family=Gochi+Hand"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">

<div class="row">
	<h2 class="col-md-6 marker-font">${param.heading}</h2>

	<div class="btn-group col-md-6 gothic-font pull-right">
		<button type="button"
			class="btn btn-large btn-warning dropdown-toggle pull-right"
			data-toggle="dropdown">What would you like to do?</button>

		<ul class="dropdown-menu">
			<li><a href="master.do?homepage=homepage">Homepage</a></li>
			<li><a href="master.do?edit=edit">Edit My Profile</a></li>
			<li><a href="master.do?viewMyRequests=viewMyRequests">View
					My Reimbursement Requests</a></li>
			<li><a href="master.do?submitRequest=submitRequest">Submit A
					Reimbursement Request</a></li>
			<%
				if (((User) session.getAttribute("user")).getPersonRole().getId() == Logic.MANAGER_ID)
				{
			%>
			<li class="divider"></li>
			<li><a href="master.do?viewEmployees=viewEmployees">View All
					Employees</a></li>
			<li><a href="master.do?viewAllRequests=viewAllRequests">View
					All Requests</a></li>
			<li><a href="master.do?createNewUser=createNewUser">Create New User</a></li>
			<%
				}
			%>
			<li class="divider"></li>
			<li><a href="master.do?logout=logout">Logout</a></li>
		</ul>
	</div>
</div>