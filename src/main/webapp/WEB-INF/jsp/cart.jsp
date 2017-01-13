<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Корзина покупок</title>
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

    <Table>
      <tr>
        <th>Наименование</th>
        <th>Цена</th>
        <th>Количество</th>
        <th></th>
      </tr>
      <c:forEach items="${cart.getMap()}" var="item">
      <tr>
        <td>${item.key.getName()}</td>
        <td>${item.key.getPrice()}</td>
        <td>${item.value}</td>
        <td>
          <form action="/CartRemove" method="post">
            <input type="hidden" name="groceryid" value="${item.key.getId()}">
            <input type="submit" value="Удалить из корзины">
          </form>
        </td>
      </tr>
      </br>
      </c:forEach>
    </Table>
    <br>
    <div>Итого:${totalprice}</div>
    <br>
    <div> <a href="/GroceryList">Продолжить покупки</a> </div>
    <div> <a href="/OrderAdd">Оформить заказ</a> </div>

  </body>
</html>
