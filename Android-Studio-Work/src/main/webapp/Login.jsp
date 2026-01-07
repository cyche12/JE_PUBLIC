<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/styles.css">
</head>
<body>

<div class="login-container">
    <h2>Login</h2>
    <form action="LoginServlet" method="post">
        <input type="text" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="submit" value="Login" class="btn">
    </form>
    <p>Don't have an account? <a href="./Registration.jsp">Register here</a></p>
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
