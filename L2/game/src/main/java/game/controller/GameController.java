package game.controller;

import game.View;
import game.model.Game;
import game.model.Model;

import java.net.*;
import java.util.*;
import game.HTTPException;
import java.io.*;

public class GameController {
    View view;
    Model model;

    public GameController(Model model, View view) {
        this.view = view;
        this.model = model;
    }


    /**
     * Checks if the GameController can handle the given method and path
     * @param method The method of which to check
     * @param path The path of which to check
     * @return true or false
     */
    public boolean canHandle(String method, String path) {
        if (method.equals("POST") && path.equals("/game/guess"))
            return true;

        if (method.equals("GET") && path.equals("/game/guess"))
            return true;

        return false;
    }

    /**
     * Handles requests of POST or GET
     * @param method The entered method
     * @param path The entered path
     * @param body The entered params
     * @param headers The entered headers
     */
    public void handle(String method, String path, String body, HashMap<String, String> headers) {
        if (method.equals("POST") && path.equals("/game/guess")) {
            try {
                handleGuess(headers, Integer.parseInt(body));
            } catch (Exception e) {
                
            }
        }

        if (method.equals("GET") && path.equals("/game/guess")) {
            handleGetGuess(headers);
        }

    }


    /**
     * API call-handled for getting the amount of guesses
     * @param headers The entered headers
     */
    private void handleGetGuess(HashMap<String, String> headers) {
        HashMap<String, String> cookieMap = Controller.createCookieMap(headers.get("Cookie"));
        String gameId = cookieMap.get("game-id");
        Game game = gameId == null ? null : model.getGame(gameId);

        if (game == null) {
            view.sendResponse(200, View.getBasicHttpHeaders(), "null");
            return;
        }

        String response = game.getResponse();
        int numberOfGuesses = game.getNumberOfGuesses();

        String responseBody = generateResponseBody(response, numberOfGuesses);

        view.sendResponse(200, View.getBasicHttpHeaders(), responseBody);

    }


    /**
     * API call-handler for entering a guess. Checks if guess is [0,100], checks if correct guess and checks if cookie is valid. 
     * @param headers
     * @param guess
     */
    private void handleGuess(HashMap<String, String> headers, int guess) {
        if (guess < 0 || guess > 100) {
            view.sendResponse(400, View.getBasicHttpHeaders(), "Number must be [0,100]");
        }

        // Check cookie
        HashMap<String, String> cookieMap = Controller.createCookieMap(headers.get("Cookie"));
        String gameId = cookieMap.get("game-id");
        Game game = gameId == null ? null : model.getGame(gameId);

        // Play game
        HashMap<String, String> resHeaders = View.getBasicHttpHeaders();

        if (game == null) {
            UUID uuid = UUID.randomUUID();
            resHeaders.put("Set-Cookie", "game-id=" + uuid + "; Path=/");
            game = new Game();
            model.addGame(uuid.toString(), game);
        }
        resHeaders.put("Content-Type", "application/json");
        game.guess(guess);
        String response = game.getResponse();
        int numberOfGuesses = game.getNumberOfGuesses();

        if (response.equals("WIN")) {
            model.removeGame(gameId);
        }

        String responseBody = generateResponseBody(response, numberOfGuesses);
        view.sendResponse(200, resHeaders, responseBody);
    }

    private String generateResponseBody(String response, int numberOfGuesses) {
        return "{\"response\": \"" + response + "\", \"numberOfGuesses\": "
                + numberOfGuesses + "}";
    }
}
