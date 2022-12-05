<%-- 
    Document   : index
    Created on : 5 Dec 2022, 11:04:46
    Author     : kalleelmdahl
--%>

<%@page import="se.kth.lab4.GuessBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Guessing game</title>
    </head>
    <body>
        <%
            GuessBean bean = (GuessBean) request.getSession().getAttribute("guessBean");
            String guessResponse = bean.getResponse();
        %>
        <span id="header">
            <%
                if (guessResponse == null) {
                    out.print("Welcome to guessing game");
                } else {
                    switch (guessResponse) {
                        case "HIGHER":
                            out.print("Nope, guess higher");
                            break;
                        case "LOWER":
                            out
                                    .print("Nope, guess lower");
                            break;
                        case "WIN":
                            out
                                    .print("You made it!!!<br>");
                            break;
                    }
                }

                if (guessResponse != null && !guessResponse.equals("WIN")) {
                    out.print("<br />You have made " + bean.getNumberOfGuesses() + " guess(es)<br />");
                }
            %>
        </span>
        <form id="form" action="/httpServer" method="GET" >
            <input type="text" name="guess">
            <input type="submit" value="Guess">
        </form>
    </body>
</html>