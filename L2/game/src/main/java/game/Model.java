package game;

import java.io.*;
import java.net.*;

public class Model {
    private int numberOfGuesses;
    private int target;

    public Model() {
        this.target = (int) Math.floor(Math.random() * 100);
        this.numberOfGuesses = 0;
    }

    public String guess(int guess) {
        numberOfGuesses++;
        if (guess > target)
            return "LOWER";

        if (guess < target)
            return "HIGHER";

        return "WIN";
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

}
