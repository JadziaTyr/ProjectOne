<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VIEW</title>
</head>
<body>
<form action="hello.do" method="post">
	<input type="text" name="hello" /> 
	<font style="color:red">
		<c:if test="${not empty error}">
			<c:out value="${error}"></c:out>
		</c:if>
	</font> 
	 <br/>
	<input type="submit" value="hello.do" /> 
</form>
<form action="goodbye.do" method="post">
	<input type="text" name="goodbye" /> 
	<font style="color:red">
		<c:if test="${not empty error}">
			<c:out value="${error}"></c:out>
		</c:if>
	</font> 
	 <br/>
	<input type="submit" value="goodebye.do" /> 
</form>
</body>
</html>