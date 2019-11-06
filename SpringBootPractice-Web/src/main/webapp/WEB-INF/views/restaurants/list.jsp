<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/restaurants/list.css" rel="stylesheet">
    <script src="/resources/js/restaurants/list.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 스토리</title>
</head>

<div id="restaurantList" class="container">
    <spring:url var="newRestaurantUri" value="/restaurants/new"/>

    <%-- 식당 목록 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="restaurantListHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 맛집 스토리 &rdquo;</h5>
        <p>&colon; 명지대 주변의 모든 식당</p>

        <div id="restaurantItemHeaderButton" class="container">
            <button id="restaurantItemNew" type="button" class="btn btn-sm btn-outline-dark" onclick="location.href='${newRestaurantUri}'">
                <i class="fas fa-pen-square"></i> 등록
            </button>
        </div>
        <hr/>
    </div>

    <%-- JSTL 리스트 2개씩 (https://roqkffhwk.tistory.com/44, https://sgroom.tistory.com/95) --%>
    <div id="restaurantListContent" class="container">
        <div class="row">
            <c:set var="i" value="0"/>
            <c:set var="j" value="2"/>

            <c:forEach var="restaurant" items="${restaurantList}" varStatus="loop">
                <c:if test="${i % j == 0}">
                    <div class="col-md-6">
                    <span style="margin-left:200px" onclick="addFavorite(${restaurant.res_index})">즐겨찾기추가</span>
                        <div class="card" onclick="getRestaurant(${restaurant.res_index})">
                            <img class="card-img" src="http://via.placeholder.com/350x150">
                            <div class="card-img-header">
                                <h4 class="card-img-text">${restaurant.res_category}</h4>
                                <div class="container headerWhiteLine">
                                </div>
                                <p class="card-img-text">${restaurant.res_name}</p>
                            </div>
                                <%-- <div class="card-profile-img">--%>
                                <%-- </div>--%>
                            <div class="card-content">
                                <p>평점: ${restaurant.res_grade}</p>
                                <p>예상 시간: ${restaurant.res_expected_minutes}</p>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${i % j == j - 1}">
                    <div class="col-md-6">
                    <span style="margin-left:200px" onclick="addFavorite(${restaurant.res_index})">즐겨찾기추가</span>
                        <div class="card" onclick="getRestaurant(${restaurant.res_index})">
                            <img class="card-img" src="http://via.placeholder.com/350x150">
                            <div class="card-img-header">
                                <h4 class="card-img-text">${restaurant.res_category}</h4>
                                <div class="container headerWhiteLine">
                                </div>
                                <p class="card-img-text">${restaurant.res_name}</p>
                            </div>
                                <%-- <div class="card-profile-img">--%>
                                <%-- </div>--%>
                            <div class="card-content">
                                <p>평점: ${restaurant.res_grade}</p>
                                <p>예상 시간: ${restaurant.res_expected_minutes}</p>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:set var="i" value="${i + 1}"/>
            </c:forEach>
        </div>
    </div>

    <%-- 게시글 목록 푸터 영역 --%>
    <%-- 페이징 또는 더보기 버튼 --%>
    <div class="container">
        <hr/>
    </div>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>