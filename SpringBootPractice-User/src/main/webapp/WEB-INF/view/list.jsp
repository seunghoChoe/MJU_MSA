<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- jstl 태그 추가 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User List</title>
</head>

<body>
	<h2>회원 목록</h2>

	<button class="btn btn-primary" onclick="location.href='/signUp'">
		회원가입</button>

	<div class="container">
		<table class="table table-hover">
			<tr>
				<th>ID.</th>
				<th>password</th>
				<th>name</th>
				<th>e-mail</th>
			</tr>
			<c:forEach var="userList" items="${users}">
				<tr>
					<td>${userList.user_id}</td>
					<td>${userList.user_password}</td>
					<td>${userList.user_name}</td>
					<td>${userList.user_email}</td>
				</tr>
			</c:forEach>

		</table>
	</div>
	<%@ include file="bootstrap.jsp"%>
</body>
</html>
