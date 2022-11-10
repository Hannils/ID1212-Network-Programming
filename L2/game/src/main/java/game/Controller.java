package game;

import java.net.*;
import java.util.*;
import java.io.*;

public class Controller implements Runnable {
    private Socket socket;
    HashMap<String, Model> games;
    View view;

    public Controller(Socket socket, HashMap<String, Model> games, View view) {
        this.games = games;
        this.socket = socket;
        this.view = view;
        /*
         * 
         * Model game = new Model();
         */
        /* games.put(uuid.toString(), game); */
    }

    public void run() {
        InputStream in;
        HashMap<String, String> headers = new HashMap<String, String>();

        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scanner reader = new Scanner(in).useDelimiter("\r\n");
        try {
            String[] meta = reader.next().split("\\s");

            // Parse first row
            if (meta.length != 3 || !(meta[0].equals("GET") || meta[0].equals("POST")) || !meta[1].equals("/")
                    || !meta[2].equals("HTTP/1.1")) {
                view.sendResponse(400, View.getBasicHttpHeaders(), "Bad Request");
                return;
            }

            System.out.println("Parsed first row");

            // Parse headers
            while (reader.hasNext()) {
                String line = reader.next();

                if (line.equals(""))
                    break;

                String[] values = line.split(": ");

                if (values.length < 2) {
                    view.sendResponse(400, View.getBasicHttpHeaders(), "Bad Request");
                    return;
                }

                headers.put(values[0], values[1]);
            }

            // Check cookie
            HashMap<String, String> cookieMap = new HashMap<String, String>();
            if (headers.get("Cookie") != null) {
                String[] cookies = headers.get("Cookie").split("; ");
                for (String cookie : cookies) {
                    String[] cookiePair = cookie.split("=");
                    cookieMap.put(cookiePair[0], cookiePair[1]);
                }
            }
            String gameId = cookieMap.get("game-id");
            System.out.println("gameCookie: " + gameId);
            Model game = games.get(cookieMap.get("game-id"));

            if (game == null) {
                System.out.println("Set cookie");
                HashMap<String, String> resHeaders = View.getBasicHttpHeaders();
                UUID uuid = UUID.randomUUID();
                resHeaders.put("Set-Cookie", "game-id=" + uuid);
                game = new Model();
                games.put(uuid.toString(), game);
                view.sendResponse(200, resHeaders, "HIGHER");

            } else {
                if (!reader.hasNext()) {
                    view.sendResponse(400, View.getBasicHttpHeaders(), "No request detected");
                    return;
                }
                System.out.println("READER");
                String rawGuess = reader.next();
                System.out.println("User guessed: " + rawGuess);
                int guess = -1;
                try {
                    guess = Integer.parseInt(rawGuess);
                } catch (Exception e) {
                    view.sendResponse(400, View.getBasicHttpHeaders(), "Write a number");
                    return;
                }

                if (guess < 0 || guess > 100) {
                    view.sendResponse(400, View.getBasicHttpHeaders(), "Number must be [0,100]");
                    return;
                }
                String res = game.guess(guess);
                view.sendResponse(200, View.getBasicHttpHeaders(),
                        "{response: " + res + ", numberOfGuesses: " + game.getNumberOfGuesses() + "}");
            }

        } finally {
            reader.close();
        }
    }
}
