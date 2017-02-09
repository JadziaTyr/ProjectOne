<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.revature.services.Logic"%>
<%@ page import="com.revature.models.User"%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View All Employees</title>

<%@ include file="resources/jsps/script_libraries.jsp"%>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">
<link href="https://fonts.googleapis.com/css?family=Gochi+Hand"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">

</head>

<body class="gothic-font">

	<div class="container">

		<jsp:include page="resources/jsps/header.jsp">
			<jsp:param name="heading" value="View All Employees" />
		</jsp:include>
		<div class="boxshadow red">
			<div class="primary row">
				<div class="panel-heading">
					<div>
						<form action="master.do" method="GET" class="pull-right">
							<input type=submit name="viewAllRequests" class="btn-warning"
								value="View All Employee Requests">
						</form>
					</div>
				</div>
			</div>

			<div class="table-responsive">
				<table id="table_id" class="table-striped table-bordered">
					<thead class="blue">
						<tr>
							<th>Name</th>
							<th>Pending Requests</th>
							<th>Resolved Requests</th>
							<th>Email</th>
						</tr>
					<thead>
					<tfoot class="blue">
						<tr>
							<th>Name</th>
							<th>Pending Requests</th>
							<th>Resolved Requests</th>
							<th>Email</th>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach items="${employeeList}" var="user">
							<tr class="orange">
								<td>${user.getFullName()}</td>
								<td>${user.getPendingCount()}</td>
								<td>${user.getResolvedCount()}</td>
								<td>${user.getEmail()}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="resources/js/myTableSource.js"></script>
</body>
</html>