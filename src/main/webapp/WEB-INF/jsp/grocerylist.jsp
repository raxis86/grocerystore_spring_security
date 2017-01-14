<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: raxis
  Date: 20.12.2016
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="req" value="${pageContext.request}" />
<html>
  <head>
    <title>Каталог товаров</title>
  </head>
  <body>
  <div class="menu">
    <c:if test="${empty user}" >
      <div> <a href="/Login">Вход</a> </div>
      <div> <a href="/Signin">Регистрация</a> </div>
    </c:if>
    <c:if test="${!empty user}" >
      <div> ${sessionScope.user.getName()}!</div>
      <div> <a href="/Logout">Выход</a> </div>
    </c:if>
  </div>

  <sec:authentication property="principal.username" />

  <div>
    <nav>
      <ul id="menu">
        <li><a href="/Index">Главная</a></li>
          <li><a href="/GroceryList">Каталог товаров</a></li>
        <sec:authorize access="hasRole('ROLE_USER')">
            <li><a href="/CartList">Корзина покупок</a></li>
            <li><a href="/OrderList">Список заказов</a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li><a href="/OrderListAdmin">Список заказов (админ-режим)</a></li>
            <li><a href="/GroceryListAdmin">Каталог товаров (админ-режим)</a></li>
        </sec:authorize>
      </ul>
    </nav>
  </div>

    <Table>
      <tr>
        <th>Наименование</th>
        <th>Цена</th>
      </tr>
      <c:forEach items="${groceryList}" var="item">
      <tr>
        <td>${item.getName()}</td>
        <td>${item.getPrice()}</td>
        <sec:authorize access="hasRole('ROLE_USER')">
          <td>
            <form action="/CartAdd" method="post">
              <input type="hidden" name="groceryid" value="${item.getId()}">
              <%--<input type="hidden" name="returnurl" value="${req.requestURI}">--%>
              <input type="submit" value="Добавить в корзину">
            </form>
          </td>
        </sec:authorize>
      </tr>
      </br>
      </c:forEach>
    </Table>

  </body>
</html>
