<%@ page import="ru.nikita.service.CompanyService" %>
<%@ page import="ru.nikita.dto.CompanyDto" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp"%>
<h2>Компании: </h2>
<ul>
    <c:forEach var="company" items="${requestScope.companies}">
        <li>
            <a href="${pageContext.request.contextPath}/employees?companyId=${company.id()}">
                    ${company.name()}
            </a>
        </li>
    </c:forEach>
</ul>
</body>
</html>
