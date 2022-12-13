/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.id1212.lab5;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 *
 * @author kalleelmdahl
 */
public class Server {

    public static void main(String[] args) throws MessagingException, IOException {

        // Create the properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.kth.se");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", true);
        props.setProperty("mail.debug", "true");

        // Create the session object
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getUserName(), getPassword());
            }
        };
        Session session = Session.getInstance(props, authenticator);
        try {
            // Create the remote object
            SendMailSender sender = new SendMailSender();
            sender.init(session, getUserName() + "@kth.se");

            // Create the registry and bind the remote object to it
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Sender", sender);

            System.out.println("Server ready.");
        } catch (AlreadyBoundException | RemoteException e) {
            System.out.println("Error in server: \n" + e.getMessage());
        }
    }
    //
    //
    //
    //
    //
    //
    //
    // 

    /*SECRET STUFF BELOW*/
    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    private static String getUserName() {
        return "Your username here";
    }
    
    private static String getPassword() {
        return "Your password here";
    }
}
