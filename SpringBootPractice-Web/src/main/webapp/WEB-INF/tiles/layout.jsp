<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%-- 부트스트랩 그리드: http://bootstrapk.com/css/ --%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%-- Core CSS Files --%>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://unpkg.com/shards-ui@latest/dist/css/shards.min.css">
    <link rel="stylesheet" href="/resources/css/style.css"/>
    <%-- Icon and Fonts--%>
    <link href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Lato:100,300,400,700,900&amp;subset=latin-ext" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,500&display=swap&subset=korean" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/gh/moonspam/NanumSquare@1.0/nanumsquare.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700,800&display=swap&subset=korean" rel="stylesheet">
    <%-- Core JS Files (jquery slim 버전은 AJAX 불가능) --%>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
<%--    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<%--    <script src="https://cdn.ckeditor.com/4.13.0/standard/ckeditor.js"></script>--%>
    <script src="https://cdn.ckeditor.com/4.13.0/full/ckeditor.js"></script>
    <script src="https://unpkg.com/shards-ui@latest/dist/js/shards.min.js"></script>
    <script src="/resources/js/style.js"></script>
</head>

<body>
<%-- Wrapper 영역 --%>
<div id="layoutWrapper">
    <%-- Header 영역 --%>
    <div id="layoutHeader"><tiles:insertAttribute name="header" ignore="true"/></div>
    <%-- Menu 영역 --%>
    <div id="layoutMenu"><tiles:insertAttribute name="menu" ignore="true"/></div>
    <%-- Content 영역 --%>
    <div id="layoutContent"><tiles:insertAttribute name="content" ignore="true"/></div>
    <%-- Footer 영역 --%>
    <div id="layoutFooter"><tiles:insertAttribute name="footer" ignore="true"/></div>
</div>
</body>
</html>