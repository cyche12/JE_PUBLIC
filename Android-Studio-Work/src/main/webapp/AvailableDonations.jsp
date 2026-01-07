<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="database.SurplusFoodDAO" %>
<%@ page import="database.SurplusFoodDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Available Donations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">
</head>
<body>

<div class="available-donations-container">
    <h2>Available Donations</h2>
    <table>
        <thead>
            <tr>
                <th>Surplus ID</th>
                <th>Food ID</th>
                <th>Retailer ID</th>
                <th>Listing Type</th>
                <th>Listing Date</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
                SurplusFoodDAO surplusFoodDAO = new SurplusFoodDAO();
                List<SurplusFoodDTO> surplusItems = surplusFoodDAO.getAllSurplusFood();
                for (SurplusFoodDTO item : surplusItems) {
            %>
            <tr>
                <td><%= item.getSurplusId() %></td>
                <td><%= item.getFoodId() %></td>
                <td><%= item.getRetailerId() %></td>
                <td><%= item.getListingType() %></td>
                <td><%= item.getListingDate() %></td>
                <td><a href="ClaimSurplusServlet?surplusId=<%= item.getSurplusId() %>">Claim</a></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>

</body>
</html>
