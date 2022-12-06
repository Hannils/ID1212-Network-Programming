/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package se.kth.quizgame;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kalleelmdahl
 */
@WebServlet(name = "QuizServerlet", urlPatterns = {"/quiz"})
public class QuizServerlet extends HttpServlet {

    private Dao database;

    @Override
    public void init() {

        try {
            database = new Dao(5432, "id1212");
        } catch (SQLException ex) {
            System.out.println("Could not connect to db: " + ex.getMessage());
            Logger.getLogger(QuizServerlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);

        String receivedUsername = request.getParameter("username");
        String receivedPassword = request.getParameter("password");

        if (receivedUsername != null && receivedPassword != null) {
            UserBean user = database.findUser(receivedUsername, receivedPassword);
            if (user != null) {
                session.setAttribute("userBean", user);
                response.sendRedirect("/quizgame/quiz");
                return;
            }
        }

        UserBean user = (UserBean) session.getAttribute("userBean");

        if (user == null) {
            System.out.println("Not logged in");

        } else {
            if (session.getAttribute("quizzes") == null) {
                ArrayList<QuizBean> quizzes = database.getQuizzes();
                session.setAttribute("quizzes", quizzes);
            }

            if (request.getParameter("category") != null) {
                ArrayList<QuestionBean> questions = database.getQuestions(
                        Integer.parseInt(request.getParameter("category"))
                );
                
                
                session.setAttribute("questions", questions);
                response.sendRedirect("/quizgame/quiz");
                return;
            }
            System.out.println("currentUser: " + user.getUsername() + " id: " + user.getId());
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");

        requestDispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
