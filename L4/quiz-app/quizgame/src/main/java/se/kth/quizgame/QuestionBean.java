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
public class QuestionBean {

    private String text;
    String[] options;

    public QuestionBean() {
    }

    public QuestionBean(String text, String[] options) {
        this.text = text;
        this.options = options;
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


    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
