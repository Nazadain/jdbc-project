<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<c:if test="${not empty sessionScope.user}">
    <form action="${pageContext.request.contextPath}/logout" method="POST">
        <button type="submit">Выйти</button>
    </form>
</c:if>
</body>
</html>
