<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<head>
    <link href="/resources/css/restaurants/newAndEdit.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/restaurants/newAndEdit.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 스토리</title>
</head>

<div id="editRestaurant" class="container">
    <spring:url var="editRestaurantUri" value="/restaurants/${restaurant.res_index}/edit"/>
    <spring:url var="restaurantListUri" value="/restaurants"/>

    <%-- 식당 수정 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="postItemHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 맛집 스토리 &rdquo;</h5>
        <p>&colon; 식당 수정</p>
        <hr/>
    </div>

    <%-- 식당 수정 컨텐트 영역 --%>
    <form:form method="put" modelAttribute="restaurant" action="${editRestaurantUri}">
        <div class="form-group">
            <label for="res_name" class="col-form-label">식당 이름</label>
            <form:input path="res_name" type="text" class="form-control" id="res_name" name="res_name" placeholder="이름은 1~50자 이내로 작성할 수 있습니다."
                        maxlength="50"/>
            <form:errors path="res_name" class="checkMessage"/>
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
        </div>

        <div class="form-group">
            <label for="res_grade" class="col-form-label">테스트용 평점</label>
            <form:input path="res_grade" type="number" value="1" min="1" max="5" class="form-control" id="res_grade" name="res_grade"/>
            <form:errors path="res_grade" class="checkMessage"/>
        </div>

        <div class="form-group">
            <label for="res_expected_minutes" class="col-form-label">예상 시간</label>
            <form:input path="res_expected_minutes" type="number" value="1" min="1" max="1000" class="form-control" id="res_expected_minutes" name="res_expected_minutes"/>
            <form:errors path="res_expected_minutes" class="checkMessage"/>
        </div>

        <div class="form-group" class="col-form-label">
            <label for="res_content" class="col-form-label">식당 소개</label>
            <form:textarea path="res_content" class="form-control" id="res_content" name="res_content" cols="10" placeholder="소개는 1~500자 이내로 작성할 수 있습니다."
                           maxlength="500"/>
            <form:errors path="res_content" class="checkMessage"/>
        </div>

        <%-- 식당 수정 푸터 영역 --%>
        <button id="editRestaurantButton" type="submit" class="btn btn-dark">수정</button>
        <button id="editRestaurantCancelButton" type="button" class="btn btn-default" onclick="location.href='${restaurantListUri}'">취소</button>
    </form:form>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>