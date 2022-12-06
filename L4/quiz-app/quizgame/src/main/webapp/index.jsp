<%-- 
    Document   : index
    Created on : 6 Dec 2022, 10:44:50
    Author     : kalleelmdahl
--%>

<%@page import="se.kth.quizgame.UserBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Start page</title>
    </head>
    <body>
        <%
            UserBean user = (UserBean) request.getSession().getAttribute("userBean");
        %>
        <%
            //Remove '@' 
            if (user == null) {
        %>
        <form method="get" action="/quizgame/quiz">
            <input type="text" name="username" placeholder="username" required>

            <input type="password" name="password" placeholder="password" required>
            <input type="submit">
        </form>

        <% } else { %>
        <h1>Hello <% out.print(user.getUsername()); %>!</h1>
        <%}%>
    </body>
</html>
