<%@ page import="database.SurplusFoodDAO, database.SurplusFoodDTO, java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Charity Dashboard</title>
</head>
<body>
<h1>Welcome, Charity User</h1>
<p>Email: <%= request.getSession().getAttribute("userEmail") %></p>
<h2>Available Food Items</h2>
<table border="1">
    <tr>
        <th>Food ID</th>
        <th>Food Name</th>
        <th>Quantity</th>
        <th>Cost</th>
        <th>Expiry Date</th>
        <th>Action</th>
    </tr>
    <%
        SurplusFoodDAO surplusFoodDAO = new SurplusFoodDAO();
        List<SurplusFoodDTO> surplusFoods = surplusFoodDAO.getDonatedSurplusFood();
        for (SurplusFoodDTO surplusFood : surplusFoods) {
    %>
    <tr>
        <td><%= surplusFood.getFoodId() %></td>
        <td><%= surplusFood.getFoodId() %></td>
        <td><%= surplusFood.getQuantity() %></td>
        <td><%= surplusFood.getFoodId() %></td>
        <td><%= surplusFood.getListingDate() %></td>
        <td><a href="ClaimDonationServlet?foodId=<%= surplusFood.getFoodId() %>">Claim</a></td>
    </tr>
    <%
        }
    %>
</table>

<h2>Claimed Food Items</h2>
<table border="1">
    <tr>
        <th>Food ID</th>
        <th>Food Name</th>
        <th>Quantity</th>
        <th>Cost</th>
        <th>Expiry Date</th>
    </tr>
    <!-- Claimed food items should be fetched and displayed here -->
</table>
</body>
</html>
