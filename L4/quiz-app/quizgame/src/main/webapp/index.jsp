<%-- 
    Document   : index
    Created on : 6 Dec 2022, 10:44:50
    Author     : kalleelmdahl
--%>

<%@page import="se.kth.quizgame.ResultBean"%>
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
            ArrayList<ResultBean> results = (ArrayList<ResultBean>) request.getSession().getAttribute("results");
            ArrayList<QuestionBean> questions = (ArrayList<QuestionBean>) request.getSession().getAttribute("questions");
        %>
        <%
            if (user == null) {
        %>
        <form method="get" action="/quizgame/quiz">
            <input type="text" name="username" placeholder="username" required>

            <input type="password" name="password" placeholder="password" required>
            <input type="submit" value="Sign in">
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
                <br>
                <label>
                    <input type="radio" name="question<%=questionCount%>" value="<%out.print(option); %>" />
                    <% out.print(option); %>
                </label>
                <% }%>
            </fieldset>
            <br>
            <% } %>
            <input type="submit" value="Get results">
        </form>
        <% } else { %>
        <p>Play a game!</p>
        <form method="get" action="/quizgame/quiz">
            <% for (QuizBean quiz : quizzes) {%>
            <label>
                <% out.print(quiz.getSubject()); %>
                <input type="radio" name="category" value="<%out.print(quiz.getId()); %>" />
            </label>
            <% } %>
            <input type="submit" value="Start quiz">
        </form>
        <p>Previous games: </p>
        <% if (results.size() == 0) { %>
        <p>No previous games :(</p>
        <% } else { %>
        <% for (ResultBean result : results) { %>
        <div>
            <p>Quiz played: <% out.print(result.getSubject()); %></p>
            <p>Score:  <% out.print(result.getScore() + "/" + result.getNumberOfQuestions()); %></p>
        </div>
        <% } %>
        <% } %>
        <%     }%>
        <%     }%>
    </body>
</html>
