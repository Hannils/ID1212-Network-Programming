package game.controller;

import java.net.*;
import java.util.*;

import game.HTTPException;
import game.View;
import game.model.Game;
import game.model.Model;

import java.io.*;
import java.lang.reflect.Method;

public class Controller implements Runnable {
    private Socket socket;
    View view;
    Model model;
    GameController gameController;
    StaticController staticController;

    public Controller(Socket socket, Model model, View view) {
        this.model = model;
        this.model = model;
        this.socket = socket;
        this.view = view;
        gameController = new GameController(model, view);
        staticController = new StaticController(view);
    }

    public void run() {
        HashMap<String, String> headers;
        HTTPMetaLine metaline;
        try {
            InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(isReader);
            String body;

            try {
                metaline = new HTTPMetaLine(reader.readLine());
                headers = readHeaders(reader);
                body = readPayload(reader);
            } catch (NumberFormatException e) {
                view.sendResponse(400, View.getBasicHttpHeaders(), "Write a number");
                return;
            } catch (HTTPException e) {
                view.sendResponse(e.getStatusCode(), View.getBasicHttpHeaders(), e.getMessage());
                return;
            }

            try {
                if (gameController.canHandle(metaline.getMethod(), metaline.getPath())) {
                    gameController.handle(metaline.getMethod(), metaline.getPath(), body, headers);

                    socket.close();
                    return;
                }

                if (staticController.canHandle(metaline.getMethod(), metaline.getPath())) {
                    staticController.handle(metaline.getPath());
                    socket.close();
                    return;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                view.sendResponse(500, View.getBasicHttpHeaders(), "Internal server error");
                return;
            }

            view.sendResponse(404, View.getBasicHttpHeaders(), "Not found");
            socket.close();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a hashmap with cookie data from a request
     * 
     * @param cookieString the value of the HTTP header line
     * @return the hashmap with cookie data
     */
    protected static HashMap<String, String> createCookieMap(String cookieString) {

        HashMap<String, String> cookieMap = new HashMap<String, String>();
        if (cookieString == null)
            return cookieMap;
        String[] cookies = cookieString.split("; ");
        for (String cookie : cookies) {
            String[] cookiePair = cookie.split("=");
            cookieMap.put(cookiePair[0], cookiePair[1]);
        }

        return cookieMap;
    }

    /**
     * Code to read the POST payload data
     * 
     * @param reader A buffered reader used for reading and parsing the incoming
     *               request
     */
    protected static String readPayload(BufferedReader reader) {
        StringBuilder payload = new StringBuilder();
        try {
            while (reader.ready()) {
                payload.append((char) reader.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payload.toString();
    }

    /**
     * Code to read the headers from the incoming request
     * 
     * @param reader A buffered reader used for reading and parsing the incoming
     *               request headers
     * @return The HashMap containing the headers as key:value pairs
     */
    private static HashMap<String, String> readHeaders(BufferedReader reader) throws HTTPException, IOException {
        String line = null;
        HashMap<String, String> headers = new HashMap<String, String>();
        while ((line = reader.readLine()).length() != 0) {

            String[] values = line.split(": ");

            if (values.length < 2) {
                throw new HTTPException(200, "Cannot parse header line");
            }
            headers.put(values[0], values[1]);
        }
        return headers;
    }
}
