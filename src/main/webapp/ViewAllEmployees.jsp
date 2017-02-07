<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.revature.services.Logic"%>
<%@ page import="com.revature.models.User"%>
   
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View All Employees</title>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">
<%@ include file="resources/jsps/script_libraries.jsp" %>  
<link href="https://fonts.googleapis.com/css?family=Gochi+Hand" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">

</head>

<body class="gothic-font">

<div class = "container">

	<jsp:include page="resources/jsps/header.jsp">
        <jsp:param name="heading" value="View All Employees"/>
    </jsp:include>
    
    <form>
    <table id = "table_id">
    <thead>
    	<tr>
    		<th>Name</th>
    		<th>Pending Requests</th>
    		<th>Resolved Requests</th>
    		<th>Email</th>
    		<th><input type = submit name = "viewAllRequests" value = "View All Employee Requests"></th>
    	</tr>
    <thead>
    <tbody>
    	<c:forEach items="${employeeList}" var = "user">
    		<tr>
    		<td>${user.getFullName()}</td>
    		<td>${user.getPendingCount()} </td>
    		<td>${user.getResolvedCount()}</td>
    		<td>${user.getEmail()}</td>
    		</tr>
    	</c:forEach>
    </tbody>
    </table>
    </form>
</div>	

<script type="text/javascript"  src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript"  src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="resources/js/myTableSource.js"></script>

</body>
</html>