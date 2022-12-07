/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.quizgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author kalleelmdahl
 */
public class Question {

    private String text;
    String[] options;
    String answer;

    public Question(String answer, String[] options, String text) {
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public QuestionBean toQuestionBean() {
        ArrayList<String> allOptions = new ArrayList<>(Arrays.asList(options));
        allOptions.add(answer);
        Collections.shuffle(allOptions);
        return new QuestionBean(
                text, allOptions.toArray(new String[0]));
    }
    
    public boolean isCorrect(String userAnswer) {
        return answer.equals(userAnswer);
    }
}
