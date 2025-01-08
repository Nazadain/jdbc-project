<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Сотрудники: </h2>
<ul>
    <c:forEach var="employee" items="${requestScope.employees}">
        <li>${employee.name()} ${employee.age()}</li>
    </c:forEach>
</ul>
</body>
</html>
