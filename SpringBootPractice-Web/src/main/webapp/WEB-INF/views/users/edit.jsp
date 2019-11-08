<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<head>
    <link href="/resources/css/users/joinAndLogin.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/users/joinAndLogin.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 계정 수정</title>
</head>

<div id="join" class="container">
    <spring:url var="editUserUri" value="/users/edit"/>
    <spring:url var="loginUri" value="/users/login"/>
    <spring:url var="homeUri" value="/"/>

    <div class="container">
        <h3>MJ Ribbon</h3>
    </div>

    <div class="card">
        <div class="card-body">
            <p><strong>계정에 등록된 가입 정보를 수정할 수 있습니다.</strong></p>
            <hr/>

            <form:form method="put" modelAttribute="user" action="${editUserUri}">
                <div class="form-group">
                    <label for="user_password" class="col-form-label">계정 비밀번호</label>
                    <form:input path="user_password" type="password" class="form-control" id="user_password" name="user_password" placeholder="Password" onblur="checkUserPassword()"/>
                    <form:errors path="user_password" id="userPasswordError" class="checkMessage"/>
                    <span id="userPasswordMessage" class="checkMessage"></span>
                </div>
                <div class="form-group">
                    <label for="user_name" class="col-form-label">사용자 이름</label>
                    <form:input path="user_name" type="text" class="form-control" id="user_name" name="user_name" placeholder="Name" onblur="checkUserName()"/>
                    <form:errors path="user_name" id="userNameError" class="checkMessage"/>
                    <span id="userNameMessage" class="checkMessage"></span>
                </div>
                <div class="form-group">
                    <label for="user_email" class="col-form-label">사용자 이메일</label>
                    <form:input path="user_email" type="text" class="form-control" id="user_email" name="user_email" placeholder="Email" onblur="checkUserEmail()"/>
                    <form:errors path="user_email" id="userEmailError" class="checkMessage"/>
                    <span id="userEmailMessage" class="checkMessage"></span>
                </div>

                <div id="buttonGroup">
                    <button id="editButton" type="submit" class="btn btn-dark btn-block">
                        <strong>계정 수정</strong>
                    </button>
                    <button id="editCancelButton" type="button" class="btn btn-default btn-block" data-toggle="tooltip" data-placement="bottom" title="홈 화면으로 가기" onclick="location.href='${homeUri}'">
                        <strong>취소</strong>
                    </button>
                </div>

                <div class="container">
                    <a href="#">ID 찾기</a>
                    <a href="#">비밀번호 찾기</a>
                    <a href="${loginUri}">로그인</a>
                </div>
            </form:form>
        </div>

        <hr/>
        <footer class="container">
            <div class="container">
                <a href="#">이용약관</a>
                <a href="#">고객센터</a>
                <a href="#">공지사항</a>
            </div>
            <a href="${homeUri}">Copyright © MJ Ribbon. All rights reserved.</a>
        </footer>
    </div>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>