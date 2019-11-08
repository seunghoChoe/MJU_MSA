<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<head>
    <link href="/resources/css/board/posts/newAndEdit.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/board/posts/newAndEdit.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 매거진</title>
</head>

<div id="newPost" class="container">
    <spring:url var="newPostUri" value="/board/posts/new"/>
    <spring:url var="postListUri" value="/board/posts"/>

    <%-- 게시글 등록 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="postItemHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 맛집 매거진 &rdquo;</h5>
        <p>&colon; 게시글 등록</p>
        <hr/>
    </div>

    <%-- 게시글 등록 컨텐트 영역 --%>
    <form:form method="post" modelAttribute="post" action="${newPostUri}">
        <div class="form-group">
            <label for="post_title" class="col-form-label">게시물 제목</label>
            <form:input path="post_title" type="text" class="form-control" id="post_title" name="post_title" placeholder="제목은 1~100자 이내로 작성할 수 있습니다."
                        maxlength="100"/>
            <form:errors path="post_title" class="checkMessage"/>
            <span id="postTitleMessage"></span>
        </div>

        <div class="form-group" class="col-form-label">
            <label for="post_content" class="col-form-label">게시물 내용</label>
            <form:textarea path="post_content" class="form-control" id="post_content" name="post_content" cols="10" placeholder="내용은 1~1000자 이내로 작성할 수 있습니다."
                           maxlength="1000"/>
            <form:errors path="post_content" class="checkMessage"/>
        </div>

        <%-- 게시글 등록 푸터 영역 --%>
        <button id="newPostButton" type="submit" class="btn btn-dark">등록</button>
        <button id="newPostCancelButton" type="button" class="btn btn-default" onclick="location.href='${postListUri}'">취소</button>
    </form:form>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>