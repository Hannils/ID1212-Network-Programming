/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package se.kth.id1212.lab5;

import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author kalleelmdahl
 */
public class Lab5 {
    
    public static void main(String[] args) throws MessagingException, IOException {
        String username = getUserName();
        String password = getPassword();
        PasswordAuthentication auth = new PasswordAuthentication(username, password);
        if (false) {
            printLatestMessage(auth);
        } else {
            sendMessage(auth);
        }
    }

    private static void printLatestMessage(PasswordAuthentication auth) throws MessagingException, IOException {
        Folder folder = null;
        Store store = null;
        try {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");
            props.setProperty("mail.imap.port", "993");
            props.setProperty("mail.imap.host", "webmail.kth.se");
            props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.imap.socketFactory.fallback", "false");
            props.setProperty("mail.imap.starttls.enable", "true");
            props.setProperty("mail.debug", "true");

            Session imapSession = Session.getInstance(props);

            store = imapSession.getStore("imap");
            //Connect to server by sending username and password.

            System.out.println("Starting to connect...");
            store.connect("webmail.kth.se", auth.getUserName(), auth.getPassword());
            //Get all mails in Inbox Forlder
            folder = store.getFolder("Inbox");
            folder.open(Folder.READ_ONLY);
            //Return result to array of message
            Message mostRecentMessage = folder.getMessage(folder.getMessageCount());
            System.out.println("No of Messages : " + folder.getMessageCount());
            System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
            System.out.println("MESSAGE:");
            System.out.println("Subject:" + mostRecentMessage.getSubject());
            MimeMultipart mp = (MimeMultipart) mostRecentMessage.getContent();
            System.out.println("Content: " + mp.getBodyPart(0).getContent());

        } finally {
            if (folder != null) {
                folder.close(true);
            }
            if (store != null) {
                store.close();
            }
        }
    }

    private static void sendMessage(PasswordAuthentication auth) throws MessagingException, IOException {

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
                return auth;
            }
        };
        Session session = Session.getInstance(props, authenticator);

        try {
            // Create the message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kelmdahl@kth.se"));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("hamnilss@kth.se"));
            message.setSubject("Hello World!");
            message.setText("This is a test message from JavaMail.");

            // Send the message
            Transport.send(message);
            System.out.println("Message sent successfully!");
        } catch (MessagingException e) {
            System.out.println("An error occurred while sending the message: \n" + e.getMessage());
        }
    }
    
    
    
    
    
    /*SECRET STUFF BELOW*/
    
    
    private static String getUserName() {
        return "Your username without @kth.se";
    }
    
    private static String getPassword() {
        return "Your password";
    }
}
