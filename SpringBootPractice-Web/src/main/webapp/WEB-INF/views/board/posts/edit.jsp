<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<head>
    <link href="/resources/css/board/posts/newAndEdit.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/board/posts/newAndEdit.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 매거진</title>
</head>

<div id="editPost" class="container">
    <spring:url var="editPostUri" value="/board/posts/${post.post_id}/edit"/>
    <spring:url var="postListUri" value="/board/posts"/>

    <%-- 게시글 수정 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="postItemHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 맛집 매거진 &rdquo;</h5>
        <p>&colon; 게시글 수정</p>
        <hr/>
    </div>

    <%-- 게시글 수정 컨텐트 영역 --%>
    <form:form method="put" modelAttribute="post" action="${editPostUri}">
<%--        <input type="hidden" name="_method" value="put"/>--%>
        <div class="form-group">
            <label for="post_title" class="col-form-label">게시물 제목 <span id="titleLengthCounter">(0 / 100자)</span></label>
            <form:input path="post_title" type="text" class="form-control" id="post_title" name="post_title" placeholder="제목은 1~100자 이내로 작성할 수 있습니다."
                        value ="${post.post_title}" maxlength="100" onKeyup="checkTitle()"/>
            <form:errors path="post_title" id="checkMessage"/>
            <span id="postTitleMessage">(0 / 100자)</span>
        </div>

        <div class="form-group" class="col-form-label">
            <label for="post_content" class="col-form-label">게시물 내용 <span id="contentLengthCounter">(0 / 1000자)</span></label>
            <form:textarea path="post_content" class="form-control" id="post_content" name="post_content" cols="10" placeholder="내용은 1~1000자 이내로 작성할 수 있습니다."
                           value ="${post.post_content}" maxlength="1000" onKeyup="checkContent()"/>
            <form:errors path="post_content" id="checkMessage"/>
        </div>

        <%-- 게시글 수정 푸터 영역 --%>
        <button id="newPostButton" type="submit" class="btn btn-dark">수정</button>
        <button id="newPostCancelButton" type="button" class="btn btn-default" onclick="location.href='${postListUri}'">취소</button>
    </form:form>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>