<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/board/posts/post.css" rel="stylesheet">
    <script src="/resources/js/board/posts/post.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 매거진</title>
</head>

<div id="postItem" class="container">
    <spring:url var="postListUri" value="/board/posts"/>

    <%-- 게시글 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="postItemHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 문의사항 상세 보기 &rdquo;</h5>
        <p>&colon; ${question.qna_question}</p>
    </div>

    <%-- 게시글 컨텐트 영역 --%>
    <div id="postItemContent" class="container">
        <p>${question.qna_question}</p>
        <hr/>
    </div>

    <%-- 게시글 푸터 영역 --%>
    <div id="postItemFooter" class="container d-flex">
        <div id="postItemFooterPostButton" class="container">
            <button id="postItemView" type="button" class="btn btn-sm btn-outline-dark"><i class="far fa-eye"></i></button>
            <button id="postItemLike" type="button" class="btn btn-sm btn-outline-dark"><i class="far fa-heart"></i></button>
            <button id="postItemComment" type="button" class="btn btn-sm btn-outline-dark"><i class="far fa-comment-dots"></i></button>
        </div>
        <div id="postItemFooterListButton" class="container ml-auto">
            <button id="postItemList" type="button" class="btn btn-sm btn-outline-dark" onclick="location.href='${postListUri}'"><i class="fas fa-list"></i> 목록</button>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>