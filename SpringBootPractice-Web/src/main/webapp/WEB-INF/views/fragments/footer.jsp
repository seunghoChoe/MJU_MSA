<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/fragments/footer.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
<%--    <script src="/resources/js/fragments/footer.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>--%>
</head>

<footer class="container">
    <spring:url var="homeUri" value="/"/>

    <div class="container text-center">
        <p id="footerTitle">명지리본: 명지대학교 맛집 검색</p>
        <a id="footerCopyright" href="${homeUri}">Copyright © MJ Ribbon. All rights reserved.</a>
    </div>
</footer>