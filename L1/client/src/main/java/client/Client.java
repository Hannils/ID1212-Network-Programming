package client;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        try {
            Socket socket = new Socket("localhost", 8090);
            System.out.print("Enter name: ");
            String name = in.nextLine();
            Runnable speaker = new Speaker(socket, name);
            Runnable listener = new Listener(socket);
            Thread speakerThread = new Thread(speaker);
            Thread listenerThread = new Thread(listener);
            speakerThread.start();
            listenerThread.start();
            try {
                speakerThread.join();
                listenerThread.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
    }
}
