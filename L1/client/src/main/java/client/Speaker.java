package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Speaker implements Runnable {
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private String uname;

    public Speaker(Socket socket, String uname) {
        this.socket = socket;
        this.uname = uname;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Connected \n ----------"); 
        String message = scanner.nextLine();
        while (!message.equalsIgnoreCase("exit") && !socket.isClosed()) {
            try {
                dataOutputStream.writeUTF(uname + ": " + message);
                message = scanner.nextLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (!socket.isClosed()) {
                dataOutputStream.writeUTF(uname + " has disconnected");
            }
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();

    }
}
