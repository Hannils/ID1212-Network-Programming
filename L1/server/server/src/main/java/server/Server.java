package server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
    private static final int port = 8090;

    public static void main(String[] args) {
        ServerSocket socket;
        ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

        try {
            socket = new ServerSocket(port);
            System.out.println("Server started on port " + socket.getLocalPort());
        } catch (IOException e) {
            System.err.println("Error starting socket");
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                Socket clientSocket = socket.accept();

                ClientHandler r = new ClientHandler(clientSocket, clients);
                clients.add(r);
                new Thread(r).start();
            } catch (Exception exception) {
                exception.printStackTrace();
                try {
                    socket.close();
                } catch (IOException ioException) {
                    System.err.println("Could not close socket" + ioException.getMessage());
                }
            }
        }

    }
}