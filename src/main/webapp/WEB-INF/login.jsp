<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Authorization</h2>
<form action="${pageContext.request.contextPath}/login" method="POST">
  <label for="username"> Username:
    <input type="text" name="username" id="username">
  </label><br>
  <label for="password">Password:
    <input type="password" name="password" id="password" required>
  </label><br>
  <input type="submit" value="Авторизоваться">
</form>
<c:if test="${param.error != null}">
    <div style="color: red">
      <span>Username of password is incorrect!</span>
    </div>
</c:if>
</body>
</html>
