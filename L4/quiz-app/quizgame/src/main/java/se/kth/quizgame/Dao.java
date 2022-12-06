/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.quizgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
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
            
            if(result.next()) {
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

    }
}
