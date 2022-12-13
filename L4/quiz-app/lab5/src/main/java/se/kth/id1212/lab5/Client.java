/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.id1212.lab5;

import java.io.Console;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import javax.mail.MessagingException;

/**
 *
 * @author kalleelmdahl
 */
public class Client {

    public static void main(String[] args) throws Exception {
        try {
            Console console = System.console();
            System.out.println("Send a message");
            System.out.print("To: ");
            String to = console.readLine();
            System.out.print("Subject: ");
            String subject = console.readLine();
            System.out.print("Message: ");
            String message = console.readLine();
            SendMail sender = (SendMail) Naming.lookup("rmi://localhost/Sender");

            sender.send(to, subject, message);

            System.out.println("Success!!!!!");
        } catch (IOException | NotBoundException | MessagingException e) {
            System.out.println("Error in client: \n" + e.getMessage());
        }
    }
}
