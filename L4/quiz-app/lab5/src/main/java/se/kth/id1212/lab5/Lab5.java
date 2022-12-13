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
        Console console = System.console();
        String username = "kelmdahl";
        String password = "9WpJhN32yHfv";
        PasswordAuthentication auth = new PasswordAuthentication(username, password);
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
}
