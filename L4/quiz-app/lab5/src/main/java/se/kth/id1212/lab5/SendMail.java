/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package se.kth.id1212.lab5;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.mail.MessagingException;

/**
 *
 * @author kalleelmdahl
 */
public interface SendMail extends Remote {
    
    void send(String to, String subject, String message) throws RemoteException, MessagingException, IOException;
}
