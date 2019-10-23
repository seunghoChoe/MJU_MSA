<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/fragments/header.css" rel="stylesheet">
<%--    <script src="/resources/js/fragments/header.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>--%>
</head>

<nav class="navbar navbar-expand-lg navbar-light bg-white mb-4">
    <spring:url var="homeUrl" value="/"/>
    <spring:url var="loginUrl" value="/users/login"/>
    <spring:url var="joinUrl" value="/users/join"/>
    <spring:url var="restaurantListUrl" value="/restaurants"/>

    <%-- Header 로고 --%>
    <img src="/resources/img/shards-logo-black.svg" alt="Example Navbar 1" class="mr-2" height="30">
    <a class="navbar-brand" href=${homeUrl}><strong>명지리본</strong></a>

    <%-- Header 메뉴 버튼 --%>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <%-- Header 메뉴 --%>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
                <a class="nav-link" href=${homeUrl}>서비스</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href=${loginUrl}>로그인</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href=${joinUrl}>계정 등록</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="https://designrevision.com" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    맛집 검색
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="${restaurantListUrl}">전체 목록</a>
                    <a class="dropdown-item" href="#">이름별 검색</a>
                    <a class="dropdown-item" href="#">평점별 검색</a>
                    <a class="dropdown-item" href="#">거리별 검색</a>
                </div>
            </li>
        </ul>
    </div>
</nav>