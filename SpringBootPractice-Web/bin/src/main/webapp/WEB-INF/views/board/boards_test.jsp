<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<head>
    <link href="/resources/css/users/joinAndLogin.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/users/joinAndLogin.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>계정 등록</title>
</head>

<div id="join" class="container">
    <spring:url var="joinUri" value="/users/join"/>
    <spring:url var="loginUri" value="/users/login"/>
    <spring:url var="homeUri" value="/"/>

    <div class="container">
        <h3>MJ Ribbon</h3>
    </div>

    <div class="card">
		<c:forEach var="post" items="${postList}">
    			<p>id : <c:out value="${post.post_id}" /></p>
			<p>title : <c:out value="${post.post_title}" /></p>
			<p>userid : <c:out value="${post.post_user_id}" /></p>
			<p>content : <c:out value="${post.post_content}" /></p>
			<p>createddate : <c:out value="${post.post_created_date}" /></p>
			<p>updated date : <c:out value="${post.post_updated_date}" /></p>
			<hr>
		</c:forEach>

    </div>
</div>

<c:if test="${serverMessage != null}">
    <script>
        var message = "${serverMessage}";
        alert(message)
    </script>
</c:if>