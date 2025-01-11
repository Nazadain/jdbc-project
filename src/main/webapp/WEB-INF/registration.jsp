<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Registration</h2>
<form action="${pageContext.request.contextPath}/registration" method="POST">
    <label for="username"> Username:
        <input type="text" name="username" id="username" value="${param.username}">
    </label><br>
    <label for="password">Password:
        <input type="password" name="password" id="password">
    </label><br>
    <label for="role"><select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option>${role}</option>
        </c:forEach>
    </select><br>
    </label>
    <input type="submit" value="Зарегистрироваться">
</form>
<c:if test="${not empty requestScope.errors}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.message}</span>
            <br>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
