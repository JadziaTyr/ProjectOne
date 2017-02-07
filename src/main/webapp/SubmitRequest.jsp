<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Submit Request</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<%@ include file="resources/jsps/script_libraries.jsp"%>
<link href="https://fonts.googleapis.com/css?family=Gochi+Hand" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">

</head>
<body class="gothic-font">

	<div class="container">

		<jsp:include page="resources/jsps/header.jsp">
			<jsp:param name="heading" value="Submit A Request" />
		</jsp:include>

		<form action="master.do" method="POST" enctype="multipart/form-data">
			<input type="number" step="any" placeholder="Amount" class="control"
				name="amount" required> <input type="text"
				placeholder="Reason" class="control" name="description" required>
			<select name="types">
				<c:forEach items="${reimbursementTypes}" var="type">
					<option value="${type.getType()}">${type.getType()}</option>
				</c:forEach>
			</select> <input type="submit" class="control btn" value="Submit"
				name="submitRequestButton"> <input type="file" name="proof">
		</form>

	</div>

</body>
</html>