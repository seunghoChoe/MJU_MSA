<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/restaurants/restaurant.css" rel="stylesheet">
    <script src="/resources/js/restaurants/restaurant.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 스토리</title>
</head>

<div id="restaurantItem" class="container">
    <spring:url var="restaurantListUri" value="/restaurants"/>

    <%-- 식당 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="restaurantItemHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 맛집 스토리 &rdquo;</h5>
        <p>&colon; ${restaurant.res_name}</p>

        <%-- FLEX 버전 (https://www.w3schools.com/bootstrap4/bootstrap_flex.asp) class 값에 p-2는 간격 --%>
        <div id="restaurantItemHeaderGroup" class="d-flex">
            <div id="restaurantItemHeaderInfo" class="container">
                <i class="fas fa-tag"></i>
                <span id="restaurantItemCategory">카테고리: ${restaurant.res_category}</span>
            </div>
            <div id="restaurantItemHeaderButton" class="container ml-auto">
                <button id="restaurantItemUri" type="button" class="btn btn-sm btn-outline-dark">URL 복사</button>
                <button id="restaurantItemEdit" type="button" class="btn btn-sm btn-outline-dark" onclick="editRestaurant(${restaurant.res_index})"><i class="fas fa-edit"></i> 수정</button>
                <button id="restaurantItemDelete" type="button" class="btn btn-sm btn-outline-dark" onclick="deleteRestaurant(${restaurant.res_index})"><i class="fas fa-eraser"></i> 삭제</button>
            </div>
        </div>
        <hr/>
    </div>

    <%-- 식당 컨텐트 영역 --%>
    <div id="restaurantItemContent" class="container">
        <%--<c:forEach var="menu" items="${restaurant.res_menus}" varStatus="loop">--%>
            <%--<p>${menu.menu_name}</p>--%>
            <%--<p>${menu.menu_price}</p>--%>
        <%--</c:forEach>--%>
            <p>${menu1.menu_index}</p>
            <p>${menu1.menu_name}</p>
            <p>${menu1.menu_price}</p>

            <p>${menu2.menu_index}</p>
            <p>${menu2.menu_name}</p>
            <p>${menu2.menu_price}</p>

            <p>${menu3.menu_index}</p>
            <p>${menu3.menu_name}</p>
            <p>${menu3.menu_price}</p>
        <hr/>
    </div>

    <%-- 식당 푸터 영역 --%>
    <div id="restaurantItemFooter" class="container d-flex">
        <div id="restaurantItemFooterPostButton" class="container">
            <button id="restaurantItemView" type="button" class="btn btn-sm btn-outline-dark"><i class="far fa-eye"></i></button>
            <button id="restaurantItemLike" type="button" class="btn btn-sm btn-outline-dark"><i class="far fa-heart"></i></button>
            <button id="restaurantItemComment" type="button" class="btn btn-sm btn-outline-dark"><i class="far fa-comment-dots"></i></button>
        </div>
        <div id="restaurantItemFooterListButton" class="container ml-auto">
            <button id="restaurantItemList" type="button" class="btn btn-sm btn-outline-dark" onclick="location.href='${restaurantListUri}'"><i class="fas fa-list"></i> 목록</button>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>