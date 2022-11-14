package game;

import java.io.*;
import java.net.*;
import java.util.*;

import game.controller.Controller;
import game.model.Game;
import game.model.Model;

public class App {
    private static ServerSocket socket;

    public static void main(String[] args) {
        Model model = new Model();
        try {
            socket = new ServerSocket(8090);
            System.out.println("Server started on port " + socket.getLocalPort());
        } catch (IOException e) {
            System.err.println("Error starting socket");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Check arguments! \n" + e);
        }

        while (true) {
            try {
                Socket clientSocket = socket.accept();

                View view = new View(new PrintWriter(clientSocket.getOutputStream()));

                Runnable r = new Controller(clientSocket, model, view);
                new Thread(r).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
