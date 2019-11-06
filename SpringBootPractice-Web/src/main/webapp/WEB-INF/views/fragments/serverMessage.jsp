<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${serverMessage ne null}">
    <script>
        var message = "${serverMessage}";
        alert(message)
    </script>
</c:if>