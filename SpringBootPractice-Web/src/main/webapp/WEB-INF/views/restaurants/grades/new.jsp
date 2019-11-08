<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<head>
    <link href="/resources/css/restaurants/grades/newAndEdit.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/restaurants/grades/newAndEdit.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 스토리</title>
</head>

<div id="newGrade" class="container">
    <spring:url var="newGradeUri" value="/restaurants/${restaurant.res_index}/grades/new"/>

    <%-- 평점 등록 컨텐트 영역 --%>
    <form:form method="post" modelAttribute="grade" action="${newGradeUri}">
        <div class="input-group">
            <form:select path="star" class="form-control" id="star" name="star">
                <form:option value="1" label="1점"/>
                <form:option value="2" label="2점"/>
                <form:option value="3" label="3점"/>
                <form:option value="4" label="4점"/>
                <form:option value="5" label="5점"/>
            </form:select>
            <form:errors path="star" class="checkMessage"/>
            <div class="input-group-append">
                <button id="newGradeButton" type="submit" class="btn btn-dark">등록</button>
            </div>
        </div>

        <%-- 평점 등록 푸터 영역 --%>
    </form:form>
</div>