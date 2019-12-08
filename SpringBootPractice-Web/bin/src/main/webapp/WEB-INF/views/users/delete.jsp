<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<head>
    <link href="/resources/css/users/joinAndLogin.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <title>명지리본: 계정 탈퇴</title>
</head>

<div id="join" class="container">
    <spring:url var="deleteUserUri" value="/users/delete"/>
    <spring:url var="loginUri" value="/users/login"/>
    <spring:url var="homeUri" value="/"/>

    <div class="container">
        <h3>MJ Ribbon</h3>
    </div>

    <div class="card">
        <div class="card-body">
            <p><strong>비밀번호 확인 후, 계정을 삭제할 수 있습니다.</strong></p>
            <hr/>

            <form method="post" action="${deleteUserUri}">
                <input type="hidden" name="_method" value="delete"/>
                <div class="form-group">
                    <label for="user_password" class="col-form-label">계정 비밀번호</label>
                    <input type="password" class="form-control" id="user_password" name="user_password" placeholder="Password"/>
                </div>

                <div id="buttonGroup">
                    <button id="deleteButton" type="submit" class="btn btn-dark btn-block">
                        <strong>계정 탈퇴</strong>
                    </button>
                    <button id="deleteCancelButton" type="button" class="btn btn-default btn-block" data-toggle="tooltip" data-placement="bottom" title="홈 화면으로 가기" onclick="location.href='${homeUri}'">
                        <strong>취소</strong>
                    </button>
                </div>

                <div class="container">
                    <a href="#">ID 찾기</a>
                    <a href="#">비밀번호 찾기</a>
                    <a href="${loginUri}">로그인</a>
                </div>
            </form>
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