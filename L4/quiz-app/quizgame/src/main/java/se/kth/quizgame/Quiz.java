/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.quizgame;

import java.util.ArrayList;

/**
 *
 * @author kalleelmdahl
 */
public class Quiz {

    private final ArrayList<Question> questions;
    private final int id;

    public Quiz(int id, ArrayList<Question> questions) {
        this.id = id;
        this.questions = questions;
    }

    public ArrayList<QuestionBean> getQuestionBeans() {
        ArrayList<QuestionBean> questionBeans = new ArrayList<>();
        for (Question question : questions) {
            questionBeans.add(question.toQuestionBean());
        }
        return questionBeans;
    }

    public int getNumberOfQuestions() {
        return questions.size();
    }

    public int getScore(ArrayList<String> answers) {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).isCorrect(answers.get(i))) {
                score++;
            }
        }
        return score;
    }

    public int getId() {
        return id;
    }

}
