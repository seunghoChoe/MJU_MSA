<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/error.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <title>명지리본: 페이지 오류</title>
</head>

<div id="error" class="container">
    <spring:url var="homeUri" value="/"/>

    <div class="container">
        <h1>${errorCode}</h1>
        <h2>Oops! ${errorMessage}</h2>
        <span>입력하신 페이지 주소가 정확한지 다시 한번 확인해보시기 바랍니다.</span>
        <br/>
        <span>관련하여 문의사항이 있으시면 고객센터를 통해 관리자에게 문의해 주시기 바랍니다.</span>
        <br/><br/>
        <span><a id="homeHref" href="${homeUri}">Return to Homepage</a></span>
    </div>

    <footer class="container">
        <div class="container">
            <a href="#">이용약관</a>
            <a href="#">고객센터</a>
            <a href="#">공지사항</a>
        </div>
        <a href="${homeUri}">Copyright © MJ Ribbon. All rights reserved.</a>
    </footer>
</div>