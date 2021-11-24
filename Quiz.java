package com.example.quizapp;

import java.io.Serializable;

public class Quiz implements Serializable {
    private String question;
    private boolean answer;
    private int color;

    public Quiz(String question, boolean answer, int color) {
        this.question = question;
        this.answer = answer;
        this.color = color;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
