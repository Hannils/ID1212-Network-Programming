/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.quizgame;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kalleelmdahl
 */
class Dao {

    private Connection conn;

    private PreparedStatement getUserByCredentials;
    private PreparedStatement getQuizzes;
    private PreparedStatement getQuestions;

    public Dao(int port, String databaseName) throws SQLException {
        connect(port, databaseName);
        prepareStatements();
    }

    public UserBean findUser(String username, String password) {
        UserBean user = null;

        try {
            getUserByCredentials.setString(1, username);
            getUserByCredentials.setString(2, password);
            ResultSet result = getUserByCredentials.executeQuery();

            if (result.next()) {
                user = new UserBean(
                        result.getString("username"),
                        result.getInt("id")
                );

            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;

    }

    public ArrayList<QuizBean> getQuizzes() {
        ArrayList<QuizBean> quizzes = new ArrayList<>();

        try {
            ResultSet result = getQuizzes.executeQuery();

            while (result.next()) {
                QuizBean quiz = new QuizBean(
                        result.getString("subject"),
                        result.getInt("id")
                );

                System.out.println("FOund quiz: " + quiz.getSubject());

                quizzes.add(quiz);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return quizzes;

    }

    public ArrayList<QuestionBean> getQuestions(int categoryId) {
        ArrayList<QuestionBean> questions = new ArrayList<>();
        try {
            getQuestions.setInt(1, categoryId);
            ResultSet result = getQuestions.executeQuery();
            while (result.next()) {
                QuestionBean question = new QuestionBean(
                        result.getString("answer"),
                        (String[]) result.getArray("options").getArray(),
                        result.getString("text"));
                System.out.println("FOund question: " + question.getText());

                questions.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return questions;
    }

    private void connect(int port, String databaseName) throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = "jdbc:postgresql://localhost:5432/id1212";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        conn = DriverManager.getConnection(url, props);
    }

    private void prepareStatements() throws SQLException {
        getUserByCredentials = conn.prepareStatement(
                "SELECT username, id from users WHERE username=? AND password=?"
        );

        getQuizzes = conn.prepareStatement(
                "SELECT id, subject FROM quizzes"
        );

        getQuestions = conn.prepareStatement(
                "SELECT text, options, answer FROM selector INNER JOIN questions ON id=question_id WHERE quiz_id=?"
        );

    }
}
