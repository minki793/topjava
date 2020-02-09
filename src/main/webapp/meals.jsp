<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br>
<table style="margin-top: 10px" class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Description</th>
        <th scope="col">Date</th>
        <th scope="col">Calories</th>
    </tr>
    </thead>
    <tbody id="mainTable">
    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <c:forEach var="mealTo" items="${mealsTo}">
         <tr style="background-color:${mealTo.excess?'red':'green'}">
            <td>${mealTo.description}</td>
            <td>
                <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
            </td>
            <td>${mealTo.calories}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
