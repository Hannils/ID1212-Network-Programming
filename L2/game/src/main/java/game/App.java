package game;

import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;
import javax.net.ssl.*;

import game.controller.Controller;
import game.model.Game;
import game.model.Model;

public class App {
    private static SSLServerSocket socket;
    private static int PORT = 8090;

    public static void main(String[] args) {
        Model model = new Model();
        try {


            // KeyStore ks = KeyStore.getInstance("JKS");
            // ks.load(new FileInputStream(
            // "/Users/hampus.nilsson/Desktop/ID1212-Network-Programming/L2/game/src/main/java/game/keystore.jks"),
            // "mule123".toCharArray());
            // KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            // kmf.init(ks, "mule123".toCharArray());
            // SSLContext sc = SSLContext.getInstance("TLS");
            // sc.init(kmf.getKeyManagers(), null, null);
            // SSLServerSocketFactory socketFactory = sc.getServerSocketFactory();


            // setup for the SSL Server socket
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");
            InputStream is = new FileInputStream("/Users/hampus.nilsson/Desktop/ID1212-Network-Programming/L2/game/src/main/java/game/keystore.jks");
            char[] pwd = "mule123".toCharArray();
            ks.load(is, pwd);
            SSLContext sslctx = SSLContext.getInstance("TLS");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, pwd);
            sslctx.init(kmf.getKeyManagers(), null, null);


            SSLServerSocketFactory  ssf = sslctx.getServerSocketFactory();
            socket = (SSLServerSocket)ssf.createServerSocket(PORT);
            System.out.println("Server started on port " + socket.getLocalPort());
        } catch (IOException e) {
            System.err.println("Error starting socket");
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while (true) {
            try {
                SSLSocket clientSocket = (SSLSocket) socket.accept();

                View view = new View(new PrintWriter(clientSocket.getOutputStream()));

                Runnable r = new Controller(clientSocket, model, view);
                new Thread(r).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
