/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatefulEjbClass.java to edit this template
 */
package se.kth.quizgame;

import javax.ejb.Stateful;

/**
 *
 * @author kalleelmdahl
 */
@Stateful
public class ResultBean {

    private String subject;
    private int score;
    private int numberOfQuestions;

    public ResultBean() {
    }

    public ResultBean(String subject, int score, int numberOfQuestions) {
        this.subject = subject;
        this.score = score;
        this.numberOfQuestions = numberOfQuestions;
    }

    /**
     * Get the value of subject
     *
     * @return the value of subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Get the value of score
     *
     * @return the value of score
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the value of numberOfQuestions
     *
     * @return the value of numberOfQuestions
     */
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
