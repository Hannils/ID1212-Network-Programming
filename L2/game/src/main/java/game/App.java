package game;

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.*;
import javax.net.ssl.*;

import game.controller.Controller;
import game.model.Game;
import game.model.Model;
import game.View;

public class App {
    private static SSLServerSocket socket;
    private static int PORT = 8090;

    public static void main(String... args) {
        Model model = new Model();
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", PORT);

        try (ServerSocket serverSocket = getServerSocket(address)) {
            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();

                System.out.println("Accepted");
                
                View view = new View(new PrintWriter(clientSocket.getOutputStream()));

                System.out.println("View created");
                
                Runnable r = new Controller(clientSocket, model, view);
                new Thread(r).start();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static ServerSocket getServerSocket(InetSocketAddress address)
            throws Exception {

        Path keyStorePath = Path.of("/Users/kalleelmdahl/key.pem");
        char[] keyStorePassword = "abc123".toCharArray();

        // Bind the socket to the given port and address
        ServerSocket serverSocket = getSslContext(keyStorePath, keyStorePassword)
                .getServerSocketFactory()
                .createServerSocket(address.getPort(), 10, address.getAddress());
        return serverSocket;
    }

    private static SSLContext getSslContext(Path keyStorePath, char[] keyStorePass)
            throws Exception {

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keyStorePath.toFile()), keyStorePass);

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, keyStorePass);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        // Null means using default implementations for TrustManager and SecureRandom
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        return sslContext;
    }
}

// keytool -genkey -keyalg RSA -keystore ~/key.pem -storetype PKCS12 -storepass abc123 -validity 365 -alias test
// keytool -exportcert -keystore ~/key.pem -storetype pkcs12 -storepass abc123 -alias test -file ~/kth.cer