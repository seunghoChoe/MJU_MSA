<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<head>
    <link href="/resources/css/restaurants/newAndEdit.css" rel="stylesheet">
    <script src="/resources/js/restaurants/newAndEdit.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 스토리</title>
</head>

<div id="newPost" class="container">
    <spring:url var="editRestaurantUri" value="/restaurants/${restaurant.res_index}/edit"/>
    <spring:url var="restaurantListUri" value="/restaurants"/>

    <%-- 식당 등록 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="postItemHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 맛집 스토리 &rdquo;</h5>
        <p>&colon; 식당 수정</p>
        <hr/>
    </div>

    <%-- 식당 등록 컨텐트 영역 --%>
    <form:form method="put" modelAttribute="restaurant" action="${editRestaurantUri}">
        <div class="form-group">
            <label for="res_name" class="col-form-label">식당 이름 <span id="nameLengthCounter">(0 / 50자)</span></label>
            <form:input path="res_name" type="text" class="form-control" id="res_name" name="res_name" placeholder="이름은 1~50자 이내로 작성할 수 있습니다."
                        maxlength="50" onKeyup="checkName()"/>
            <form:errors path="res_name" class="checkMessage"/>
            <span id="resNameMessage"></span>
        </div>

        <div class="form-group">
            <label for="res_category" class="col-form-label">식당 카테고리</label>
            <form:select path="res_category" class="form-control" id="res_category" name="res_category">
                <form:option value="한식" label="한식"/>
                <form:option value="분식" label="분식"/>
                <form:option value="중식" label="중식"/>
                <form:option value="일식" label="일식"/>
                <form:option value="양식" label="양식"/>
            </form:select>
            <form:errors path="res_category" class="checkMessage"/>
            <span id="resCategoryMessage"></span>
        </div>

        <div class="form-group">
            <label for="res_expected_minutes" class="col-form-label">예상 시간</label>
            <form:input path="res_expected_minutes" type="text" class="form-control" id="res_expected_minutes" name="res_expected_minutes"/>
            <form:errors path="res_expected_minutes" class="checkMessage"/>
            <span id="resExpectedMinutesMessage"></span>
        </div>

        <%-- 식당 등록 푸터 영역 --%>
        <button id="editRestaurantButton" type="submit" class="btn btn-dark">수정</button>
        <button id="editRestaurantCancelButton" type="button" class="btn btn-default" onclick="location.href='${restaurantListUri}'">취소</button>
    </form:form>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>