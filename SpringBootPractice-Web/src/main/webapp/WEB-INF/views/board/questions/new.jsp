<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<head>
    <link href="/resources/css/board/posts/newAndEdit.css" rel="stylesheet">
    <script src="/resources/js/board/posts/newAndEdit.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 매거진</title>
</head>

<div id="newPost" class="container">
    <spring:url var="newQuestionUri" value="/board/questions/new"/>
    <spring:url var="questionListUri" value="/board/questions"/>

    <%-- 게시글 등록 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="postItemHeader" class="container header">
        <h5 class="headerStrongText">&ldquo;  문의사항 등록 &rdquo;</h5>
        <p>&colon;  문의사항 등록</p>
        <hr/>
    </div>

    <%-- 게시글 등록 컨텐트 영역 --%>
    <form:form method="post" modelAttribute="post" action="${newQuestionUri}">
        <label class="col-form-label">문의사항 카테고리 </label>
        <input class="form-control" type="text" id="qna_category" name="qna_category">
        
        <label for="qna_question" class="col-form-label">문사사항 내용 </label>
        <input class="form-control" type="text" id="qna_question" name="qna_question">
		<br>
		
        <%-- 게시글 등록 푸터 영역 --%>
        <button id="newPostButton" type="submit" class="btn btn-dark">등록</button>
        <button id="newPostCancelButton" type="button" class="btn btn-default" onclick="location.href='${questionListUri}'">취소</button>
    </form:form>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>