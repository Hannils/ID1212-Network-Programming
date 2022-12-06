<%-- 
    Document   : index
    Created on : 6 Dec 2022, 10:44:50
    Author     : kalleelmdahl
--%>

<%@page import="se.kth.quizgame.QuestionBean"%>
<%@page import="se.kth.quizgame.QuizBean"%>
<%@page import="java.util.ArrayList"%>
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
            ArrayList<QuizBean> quizzes = (ArrayList<QuizBean>) request.getSession().getAttribute("quizzes");
            ArrayList<QuestionBean> questions = (ArrayList<QuestionBean>) request.getSession().getAttribute("questions");
        %>
        <%
            if (user == null) {
        %>
        <form method="get" action="/quizgame/quiz">
            <input type="text" name="username" placeholder="username" required>

            <input type="password" name="password" placeholder="password" required>
            <input type="submit">
        </form>

        <% } else { %>
        <h1>Hello <% out.print(user.getUsername()); %>!</h1>
        <% if (questions != null) { %>
        <p>Welcome to Quiz</p>
        <form method="get" action="/quizgame/quiz">
            <% int questionCount = 0; %>
            <% for (QuestionBean question : questions) { %>
            <fieldset>
                <legend>Question <% out.print(++questionCount);%></legend>
                <p><% out.print(question.getText()); %></p>
                <% for (String option : question.getOptions()) {%>
                <label>
                    <input type="radio" name="question<%=questionCount%>" value="<%out.print(option); %>" />
                    <% out.print(option); %>
                </label><br>
                <% }%>
                <label>
                    <input type="radio" name="question<%=questionCount%>" value="<%out.print(question.getAnswer()); %>" />
                    <% out.print(question.getAnswer()); %>
                </label>
            </fieldset>
            <% } %>
            <input type="submit">
        </form>
        <% } else { %>
        <p>Choose</p>
        <form method="get" action="/quizgame/quiz">
            <% for (QuizBean quiz : quizzes) {%>
            <label>
                <% out.print(quiz.getSubject()); %>
                <input type="radio" name="category" value="<%out.print(quiz.getId()); %>" />
            </label>
            <% } %>
            <input type="submit">
        </form>
        <% } %>
        <%     }%>
    </body>
</html>
