<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.revature.models.User" %>
<%@ page import="com.revature.services.Logic" %>
<link href="https://fonts.googleapis.com/css?family=Gochi+Hand" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
    
<!DOCTYPE>

<div class="btn-group col-md-2 gothic-font">
    <button type="button" class="btn btn-large btn-default dropdown-toggle" data-toggle="dropdown">
        What would you like to do? 
    </button>
    <ul class="dropdown-menu">
        <li><a href="master.do?homepage=homepage">Homepage</a></li>
        <li><a href="master.do?edit=edit">Edit My Profile</a></li>
        <li><a href="master.do?viewMyRequests=viewMyRequests">View My Reimbursement Requests</a></li>
        <li><a href="master.do?submitRequest=submitRequest">Submit A Reimbursement Request</a></li>
		<% if( ((User)session.getAttribute("user")).getPersonRole().getId() ==  Logic.MANAGER_ID) {%>
		<li class="divider"></li>
		<li><a href="master.do?viewEmployees=viewEmployees">View All Employees</a></li>
		<li><a href="master.do?viewAllRequests=viewAllRequests">View All Requests</a></li>
		<%} %>
		<li class="divider"></li>
		<li><a href="master.do?logout=logout">Logout</a></li>
    </ul>
</div>  
