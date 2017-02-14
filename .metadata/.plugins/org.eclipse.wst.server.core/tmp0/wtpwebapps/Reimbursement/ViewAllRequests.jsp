<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="com.revature.services.Logic"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Requests</title>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">
<%@ include file="resources/jsps/script_libraries.jsp"%>
<link rel="stylesheet" type="text/css" href="resources/css/modal.css">
<link href="https://fonts.googleapis.com/css?family=Gochi+Hand"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">

</head>

<body class="gothic-font">

	<div class="container">

		<jsp:include page="resources/jsps/header.jsp">
			<jsp:param name="heading" value="Review Requests" />
		</jsp:include>

		<%@ include file="resources/jsps/script_libraries.jsp"%>

		<div class="boxshadow red">
			<form action="master.do" method="POST">
				<div class="table-responsive">
					<table id="table_id" class="table-striped">
						<thead class="blue">
							<tr>
								<th>Name</th>
								<th>Date Requested</th>
								<th>Reason</th>
								<th>Amount</th>
								<th>Description</th>
								<th>File</th>
								<th>Status</th>
								<th>Date Resolved</th>
								<th>Resolved By</th>
							</tr>
						</thead>
						<tfoot class = "blue">
							<tr>
								<th>Name</th>
								<th>Date Requested</th>
								<th>Reason</th>
								<th>Amount</th>
								<th>Description</th>
								<th>File</th>
								<th>Status</th>
								<th>Date Resolved</th>
								<th>Resolved By</th>
							</tr>
						</tfoot>
						<tbody>
							<c:forEach items="${reimbursementList}" var="re">
								<tr id="${re.getId()}"  class = "orange">
									<td>${re.getRequester().getFullName()}</td>
									<td><fmt:formatDate pattern="dd MMM yyyy"
											value="${re.getSubmittedTimestamp()}" /></td>
									<td>${re.getReimbursementType().getType()}</td>
									<td><fmt:formatNumber value="${re.getAmount()}"
											type="currency" /></td>
									<td>${re.getDescription()}</td>
									<td>
									<c:if test="${fn:length(re.getImage()) > 0}">
										<span id="${re.getId()}" class="filelink">Proof</span>
									</c:if>
									</td>
									<c:choose>
										<c:when test="${user.getId() != re.getRequester().getId()}">
											<c:choose>
												<c:when
													test="${re.getReimbursementStatus().getStatus() == 'Pending'}">
													<td><select name="${re.getId()}" class="blue img-rounded">
															<c:forEach items="${Logic.getLogic().getStatusList()}"
																var="status">
																<option value="${status.getStatus()}">
																	${status.getStatus()}</option>
															</c:forEach>
													</select></td>
													<td></td>
													<td></td>
												</c:when>
												<c:otherwise>
													<td>${re.getReimbursementStatus().getStatus()}</td>
													<td><fmt:formatDate pattern="dd MMM yyyy"
															value="${re.getResolvedTimestamp()}" /></td>
													<td>${re.getApprover().getFullName()}</td>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<td>${re.getReimbursementStatus().getStatus()}</td>
											<c:choose>
												<c:when
													test="${re.getReimbursementStatus().getStatus() == 'Pending'}">
													<td></td>
													<td></td>
												</c:when>
												<c:otherwise>
													<td><fmt:formatDate pattern="dd MMM yyyy"
															value="${re.getResolvedTimestamp()}" /></td>
													<td>${re.getApprover().getFullName()}</td>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<input name="submitResolvedRequests" class="btn-warning" type="Submit" value="Submit">
			</form>
		</div>

		<!-- The Modal -->
		<div id="myModal" class="modal">
			<span class="close">&times;</span> <img class="modal-content"
				id="img01">
			<div id="caption"></div>
		</div>

	</div>
	<!-- end container -->

	<script type="text/javascript"
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="resources/js/myTableSource.js"></script>
	<script type="text/javascript" src="resources/js/myImageSource.js"></script>

</body>
</html>