<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
</head>
<body>
	<button class="btn btn-primary" onclick="location.href='/signUp'">
		회원가입</button>
	<c:if test="${member==null}">
		<input type="button" value="로그인" onclick="location.href='/login_form'">
	</c:if>
	<c:if test="${member!=null}">
		<h3>${member.user_id} 님 반갑습니다!</h3>
		<input type="button" value="로그아웃" onclick="location.href='/logout'">
	</c:if>
</body>
</html>