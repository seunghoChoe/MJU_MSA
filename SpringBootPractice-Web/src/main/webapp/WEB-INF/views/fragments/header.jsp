<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/fragments/header.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
<%--    <script src="/resources/js/fragments/header.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>--%>
</head>

<nav class="navbar navbar-expand-lg navbar-light bg-white mb-4">
    <spring:url var="homeUri" value="/"/>
    <spring:url var="loginUri" value="/users/login"/>
    <spring:url var="logoutUri" value="/users/logout"/>
    <spring:url var="joinUri" value="/users/join"/>

    <%-- 계정 관리 --%>
    <spring:url var="userListUri" value="/users"/>
    <spring:url var="editUserUri" value="/users/edit"/>
    <spring:url var="deleteUserUri" value="/users/delete"/>
    <%-- 게시판 --%>
    <spring:url var="postListUri" value="/board/posts"/>
    <spring:url var="newPostUri" value="/board/posts/new"/>
    <spring:url var="questionListUri" value="/board/questions"/>
    <spring:url var="questionPostUri" value="/board/questions/new"/>
    <%-- 식당 --%>
    <spring:url var="restaurantListUri" value="/restaurants"/>
    <spring:url var="myRestaurantListUri" value="/favorites"/>
    <spring:url var="newRestaurantUri" value="/restaurants/new"/>

    <%-- Header 로고 --%>
    <img src="/resources/img/shards-logo-black.svg" alt="Example Navbar 1" class="mr-2" height="30">
    <a class="navbar-brand" href=${homeUri}><strong>명지리본</strong></a>

    <%-- Header 메뉴 버튼 --%>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <%-- Header 메뉴 --%>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
                <a class="nav-link" href=${homeUri}>명지리본</a>
            </li>
            <c:if test="${empty sessionScope.loggedUser}">
                <li class="nav-item">
                    <a class="nav-link" href=${loginUri}>로그인</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href=${joinUri}>계정 등록</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href=${userListUri}>전체 계정 조회</a>
                </li>
            </c:if>
            <c:if test="${not empty sessionScope.loggedUser}">
                <li class="nav-item">
                    <a class="nav-link" href=${logoutUri}>로그아웃</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownUserMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        계정 관리
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownUserMenuLink">
                        <a class="nav-link" href=${editUserUri}>계정 수정</a>
                        <a class="nav-link" href=${deleteUserUri}>계정 탈퇴</a>
                    </div>
                </li>
            </c:if>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownBoardMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    맛집 매거진
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownBoardMenuLink">
                    <a class="dropdown-item" href="${postListUri}">게시글 목록</a>
                    <a class="dropdown-item" href="${newPostUri}">게시글 등록</a>
                    <a class="dropdown-item" href="${questionListUri}">문의사항 조회</a>
					<a class="dropdown-item" href="${questionPostUri}">문의사항 작성</a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownRestaurantMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    맛집 스토리
               </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownRestaurantMenuLink">
                    <a class="dropdown-item" href="${myRestaurantListUri}">내 식당 목록</a>
                    <a class="dropdown-item" href="${restaurantListUri}">식당 목록</a>
                    <a class="dropdown-item" href="${newRestaurantUri}">식당 등록</a>
                </div>
            </li>
        </ul>
    </div>
</nav>