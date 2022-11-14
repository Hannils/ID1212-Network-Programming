package game.model;

import java.io.*;
import java.net.*;

public class Game {
    private int numberOfGuesses;
    private int target;
    private String response;

    public Game() {
        target = (int) Math.floor(Math.random() * 100);
        numberOfGuesses = 0;
        response = null;
    }

    public void guess(int guess) {
        numberOfGuesses++;
        System.out.println("Kommer hit! " + target + "  " + numberOfGuesses);
        if (guess > target)
            response = "LOWER";
        else if (guess < target)
            response = "HIGHER";
        else
            response = "WIN";
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public String getResponse() {
        return response;
    }

}
