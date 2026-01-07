<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consumer Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">
</head>
<body>
    <div class="header">
        <div class="header-right">
            <a href="ConsumerServlet">Subscription</a>
            <span class="account-info">${sessionScope.email}</span>
            <a href="LogoutServlet">Logout</a>
        </div>
    </div>
    <div class="dashboard-container">
        <h2>Welcome, Consumer User</h2>
        <p>Email: ${sessionScope.email}</p>
        
        <h3>Available Food Items</h3>
        <table class="table-center">
            <thead>
                <tr>
                    <th>Food ID</th>
                    <th>Food Name</th>
                    <th>Quantity</th>
                    <th>Cost</th>
                    <th>Expiry Date</th>
                    <th>Retailer ID</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${availableItems}">
                    <tr>
                        <td>${item.foodId}</td>
                        <td>${item.foodName}</td>
                        <td>${item.foodQuantity}</td>
                        <td>${item.foodCost}</td>
                        <td>${item.foodExpiry}</td>
                        <td>${item.retailerId}</td>
                        <td>
                            <form action="PurchaseServlet" method="post">
                                <input type="hidden" name="foodId" value="${item.foodId}">
                                <input type="submit" value="Purchase">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <h3>Purchased Food Items</h3>
        <table class="table-center">
            <thead>
                <tr>
                    <th>Food ID</th>
                    <th>Food Name</th>
                    <th>Quantity</th>
                    <th>Cost</th>
                    <th>Expiry Date</th>
                    <th>Retailer ID</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${purchasedItems}">
                    <tr>
                        <td>${item.foodId}</td>
                        <td>${item.foodName}</td>
                        <td>${item.foodQuantity}</td>
                        <td>${item.foodCost}</td>
                        <td>${item.foodExpiry}</td>
                        <td>${item.retailerId}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
