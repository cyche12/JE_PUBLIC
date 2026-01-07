<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.FoodItemDAO" %>
<%@ page import="database.FoodItemDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Claim Item</title>
    <link rel="stylesheet" href="styles/styles.css">
</head>
<body>
<h1>Claim Item</h1>
<%
    String foodIdParam = request.getParameter("foodId");
    if (foodIdParam != null) {
        int foodId = Integer.parseInt(foodIdParam);
        FoodItemDAO foodItemDAO = new FoodItemDAO();
        FoodItemDTO foodItem = foodItemDAO.getFoodItemById(foodId);
        if (foodItem != null) {
%>
<div>
    <h2>Item Details</h2>
    <p>Food ID: <%= foodItem.getFoodId() %></p>
    <p>Food Name: <%= foodItem.getFoodName() %></p>
    <p>Quantity: <%= foodItem.getFoodQuantity() %></p>
    <p>Cost: <%= foodItem.getFoodCost() %></p>
    <p>Expiry Date: <%= foodItem.getFoodExpiry() %></p>
    <p>Retailer ID: <%= foodItem.getRetailerId() %></p>
	<form action="<%= request.getContextPath() %>/PurchaseServlet" method="post">
	    <input type="hidden" name="foodId" value="<%= foodItem.getFoodId() %>">
	    <input type="submit" value="Purchase">
	</form>
</div>
<%
        } else {
%>
<p>Item not found.</p>
<%
        }
    } else {
%>
<p>Invalid item ID.</p>
<%
    }
%>
</body>
</html>
