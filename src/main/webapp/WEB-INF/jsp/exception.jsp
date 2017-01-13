<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: raxis
  Date: 25.12.2016
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Доступ запрещен!</title>
</head>
<body>
    <div class="menu">
        <c:if test="${empty sessionScope.user}" >
            <div> <a href="/Login">Вход</a> </div>
            <div> <a href="/Signin">Регистрация</a> </div>
        </c:if>
        <c:if test="${!empty sessionScope.user}" >
            <div> ${sessionScope.user.getName()}!</div>
            <div> <a href="/Logout">Выход</a> </div>
        </c:if>
    </div>

    <div>
        <nav>
            <ul id="menu">
                <li><a href="/Index">Главная</a></li>
                <c:if test="${empty sessionScope.user}" >
                    <li><a href="/GroceryList">Каталог товаров</a></li>
                </c:if>
                <c:if test="${!empty sessionScope.user}" >
                    <c:if test="${!sessionScope.role.getName().equals('admin')}" >
                        <li><a href="/GroceryList">Каталог товаров</a></li>
                        <li><a href="/CartList">Корзина покупок</a></li>
                        <li><a href="/OrderList">Список заказов</a></li>
                    </c:if>
                    <c:if test="${sessionScope.role.getName().equals('admin')}" >
                        <li><a href="/OrderListAdmin">Список заказов (админ-режим)</a></li>
                        <li><a href="/GroceryListAdmin">Каталог товаров (админ-режим)</a></li>
                    </c:if>
                </c:if>
            </ul>
        </nav>
    </div>


    <%--<H2>${message.replaceAll("(\\r\\n|\\n)", "<br />")}</H2>--%>

    <c:set var="newLine" value="(\\r\\n|\\n)" />
    <c:set var="splitMessage" value="${message.split(newLine)}" />

    <H2>${splitMessage[0]}</H2>

    <c:forEach var="i" begin="1" end="${fn:length(splitMessage)}">
        <c:out value="${splitMessage[i]}"/><br>
    </c:forEach>

</body>
</html>
