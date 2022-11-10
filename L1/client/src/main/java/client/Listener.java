package client;

import java.io.*;
import java.net.*;

class Listener implements Runnable {
    private InputStreamReader inputStreamReader;
    private Socket socket;

    private char[] buffer;

    public Listener(Socket socket) {
        buffer = new char[2048];
        this.socket = socket;
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.inputStreamReader = inputStreamReader;
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        try {
            int readBytes;
            while ((readBytes = inputStreamReader.read(buffer, 0, 2048)) != -1) {
                String message = new String(buffer, 0, readBytes);
                System.out.print("\n" + message + "\n");
            }

            System.out.println("Server closed :/");
            socket.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}