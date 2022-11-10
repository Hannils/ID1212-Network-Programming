package server;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

class ClientHandler implements Runnable {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private ArrayList<ClientHandler> clients;
    private char[] buffer;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
        buffer = new char[2048];
        this.socket = socket;
        this.clients = clients;

        for (ClientHandler client : clients) {
            if (!client.equals(this)) {
                client.sendMessage("\n-------- A new user joined the chat! --------");
            }
        }

    }

    public void run() {
        try {

            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            int readBytes;
            while ((readBytes = streamReader.read(buffer, 0, 2048)) != -1) {
                String message = new String(buffer, 0, readBytes);

                for (ClientHandler client : clients) {
                    if (!client.equals(this)) {
                        client.sendMessage(message);
                    }
                }
            }
            clients.remove(this);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMessage(String message) {
        try {
            dataOutputStream.writeBytes(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}