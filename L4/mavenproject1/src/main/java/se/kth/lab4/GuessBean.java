/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatefulEjbClass.java to edit this template
 */
package se.kth.lab4;

import javax.ejb.Stateful;

/**
 *
 * @author kalleelmdahl
 */
@Stateful
public class GuessBean {
    private int numberOfGuesses;
    private final int target;
    private String response;

    public GuessBean() {
        target = (int) Math.floor(Math.random() * 100);
        numberOfGuesses = 0;
        response = null;
    }
    /**
     * Checks the conditions for an entered guess
     * @param guess The int of which to check
     */
    public void guess(int guess) {
        numberOfGuesses++;
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