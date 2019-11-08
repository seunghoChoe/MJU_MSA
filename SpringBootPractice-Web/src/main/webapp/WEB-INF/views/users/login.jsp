<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/users/joinAndLogin.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/users/joinAndLogin.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 계정 로그인</title>
</head>

<div id="login" class="container">
    <spring:url var="joinUri" value="/users/join"/>
    <spring:url var="loginUri" value="/users/login"/>
    <spring:url var="homeUri" value="/"/>

    <div class="container">
        <h3>MJ Ribbon</h3>
    </div>

    <div class="card">
        <div class="card-body">
            <p><strong>계정 정보를 입력하여 로그인하세요!</strong></p>
            <hr/>

            <form method="post" action="${loginUri}">
                <div class="form-group">
                    <label for="user_id" class="col-form-label">계정 ID</label>
                    <div class="input-group input-group-seamless">
                        <span class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fas fa-user"></i>
                            </span>
                        </span>
                        <input type="text" class="form-control" id="user_id" name="user_id" placeholder="ID"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="user_password" class="col-form-label">계정 비밀번호</label>
                    <div class="input-group input-group-seamless">
                        <span class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fas fa-lock"></i>
                            </span>
                        </span>
                        <input type="password" class="form-control" id="user_password" name="user_password" placeholder="Password"/>
                    </div>
                </div>

                <div id="buttonGroup">
                    <button id="loginButton" type="submit" class="btn btn-dark btn-block">
                        <strong>계정 로그인</strong>
                    </button>
                    <button id="loginCancelButton" type="button" class="btn btn-default btn-block" data-toggle="tooltip" data-placement="bottom" title="홈 화면으로 가기" onclick="location.href='${homeUri}'">
                        <strong>취소</strong>
                    </button>
                </div>

                <div class="container">
                    <a href="#">ID 찾기</a>
                    <a href="#">비밀번호 찾기</a>
                    <a href="${joinUri}">계정 등록</a>
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