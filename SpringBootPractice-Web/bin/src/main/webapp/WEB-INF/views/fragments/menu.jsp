<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/fragments/menu.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
<%--    <script src="/resources/js/fragments/menu.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>--%>
</head>

<div class="container">
    <%-- 이름 검색 구현 후, URI 지정해야 함 --%>
    <spring:url var="searchUri" value="/"/>
    <form method="get" action="${searchUri}">
        <fieldset>
            <legend>솔직한 리뷰, 믿을 수 있는 평점! 명지리본!</legend>
            <div class="input-group">
                <input type="text" class="form-control" placeholder="맛집 이름을 검색하세요!">
                <div class="input-group-append">
                    <%-- 검색 버튼 --%>
                    <button class="btn btn-dark" type="button" data-toggle="tooltip" data-placement="bottom" title="맛집 이름을 검색하세요!">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </div>
        </fieldset>
    </form>
</div>