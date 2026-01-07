<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="database.FoodItemDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retail Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">
    <style>
        .message {
            color: red;
            font-weight: bold;
            margin-left: 10px;
        }
        .table-center th, .table-center td {
            text-align: center;
            vertical-align: middle;
        }
        .message-container {
            display: flex;
            justify-content: flex-start;
            align-items: center;
            height: 20px;
            margin-top: 10px;
        }
        .action-cell {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            justify-content: center;
        }
        .action-container {
            display: flex;
            align-items: center;
        }
        .action-container select, .action-container button {
            margin-right: 10px;
        }
        .header-right {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .header-right a, .header-right span {
            margin-left: 10px;
        }
    </style>
    <script>
        function listSurplus(foodId) {
            var listingType = document.getElementById('listingType-' + foodId).value;
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "ListSurplusServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    document.getElementById('message-' + foodId).innerText = xhr.responseText;
                }
            };
            xhr.send("foodId=" + foodId + "&listingType=" + listingType);
        }
    </script>
</head>
<body>
    <div class="header">
        <div class="header-right">
            <a href="Subscription.jsp">Subscription</a>
            <span class="account-info">${userEmail}</span>
            <a href="LogoutServlet">Logout</a>
        </div>
    </div>
    <div class="dashboard-container">
        <h2>Welcome, Retail User</h2>
        <p>Email: ${userEmail}</p>
        <a href="AddFoodItem.jsp" class="add-food-button">Add Food Item</a>
        <h3>Surplus Items</h3>
        <table class="table-center" style="table-layout: fixed; width: 100%;">
            <thead>
                <tr>
                    <th>Food ID</th>
                    <th>Food Name</th>
                    <th>Quantity</th>
                    <th>Cost</th>
                    <th>Expiry Date</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<FoodItemDTO> surplusItems = (List<FoodItemDTO>) request.getAttribute("surplusItems");
                    if (surplusItems != null) {
                        for (FoodItemDTO item : surplusItems) {
                %>
                <tr>
                    <td><%= item.getFoodId() %></td>
                    <td><%= item.getFoodName() %></td>
                    <td id="retail-quantity-<%= item.getFoodId() %>"><%= item.getFoodQuantity() %></td>
                    <td><%= item.getFoodCost() %></td>
                    <td><%= item.getFoodExpiry() %></td>
                    <td class="action-cell">
                        <div class="action-container">
                            <select id="listingType-<%= item.getFoodId() %>" name="listingType">
                                <option value="DONATION">Donation</option>
                                <option value="DISCOUNT_SALE">Discount Sale</option>
                            </select>
                            <button onclick="listSurplus(<%= item.getFoodId() %>)">List Surplus</button>
                        </div>
                        <div class="message-container">
                            <span id="message-<%= item.getFoodId() %>" class="message"></span>
                        </div>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6">No surplus items found.</td>
                </tr>
                <% 
                    } 
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
