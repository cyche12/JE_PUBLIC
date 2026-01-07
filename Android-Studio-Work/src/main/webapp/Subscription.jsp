<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subscription Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles3.css">
</head>
<body>
    <div class="subscription-container">
        <h2>Subscribe for Surplus Food Notifications</h2>
        
        <form action="SubscriptionServlet" method="post">
            <div class="form-group">
                <label for="userId">User ID</label>
                <input type="text" id="userId" name="userId" placeholder="User ID" required>
            </div>
            <div class="form-group">
                <label for="location">Location</label>
                <input type="text" id="location" name="location" placeholder="Location" required>
            </div>
            <div class="form-group">
                <label for="communicationMethod">Communication Method</label>
                <select id="communicationMethod" name="communicationMethod" required>
                    <option value="" disabled selected>Select Communication Method</option>
                    <option value="EMAIL">Email</option>
                    <option value="PHONE">Phone</option>
                </select>
            </div>
            <div class="form-group">
                <label for="foodPreferences">Food Preferences</label>
                <input type="text" id="foodPreferences" name="foodPreferences" placeholder="Food Preferences">
            </div>
            <button type="submit" class="btn btn-primary">Subscribe</button>
        </form>
        
        <%
            String error = (String) request.getAttribute("error");
            String message = (String) request.getAttribute("message");
            if (error != null) {
        %>
            <div class="alert alert-danger"><%= error %></div>
        <%
            }
            if (message != null) {
        %>
            <div class="alert alert-success"><%= message %></div>
        <%
            }
        %>
    </div>
</body>
</html>
