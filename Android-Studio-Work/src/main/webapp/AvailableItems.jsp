<%@ page import="database.FoodItemDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="database.FoodItemDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Available Items</title>
</head>
<body>
<h1>Available Items</h1>
<table border="1">
    <tr>
        <th>Food ID</th>
        <th>Food Name</th>
        <th>Quantity</th>
        <th>Cost</th>
        <th>Expiry Date</th>
        <th>Retailer ID</th>
        <th>Action</th>
    </tr>
    <%
        FoodItemDAO foodItemDAO = new FoodItemDAO();
        List<FoodItemDTO> foodItems = foodItemDAO.getAllFoodItems();
        for (FoodItemDTO item : foodItems) {
    %>
    <tr>
        <td><%= item.getFoodId() %></td>
        <td><%= item.getFoodName() %></td>
        <td><%= item.getFoodQuantity() %></td>
        <td><%= item.getFoodCost() %></td>
        <td><%= item.getFoodExpiry() %></td>
        <td><%= item.getRetailerId() %></td>
        <td><a href="claimItem.jsp?foodId=<%= item.getFoodId() %>">Purchase</a></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
