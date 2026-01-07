<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">
</head>
<body>

<div class="register-container">
    <h2>Register</h2>
    
    <form action="RegistrationServlet" method="post">
        <input type="text" name="firstName" placeholder="First Name" required>
        <input type="text" name="lastName" placeholder="Last Name" required>
        <input type="email" name="userEmail" placeholder="Email" required>
        <input type="password" name="userPassword" placeholder="Password" required>
        <select name="userType" required>
            <option value="" disabled selected>Select User Type</option>
            <option value="RETAIL">Retail</option>
            <option value="CONSUMER">Consumer</option>
            <option value="CHARITY">Charity</option>
        </select>
        
        <input type="date" name="joinDate" required>
        <input type="submit" value="Register">
        <input type="button" value="Back to Home" onclick="window.location.href='index.jsp'">
    </form>
    
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="error" style="color:red;"><%= error %></div>
    <%
        }
    %>
    
</div>

</body>
</html>
