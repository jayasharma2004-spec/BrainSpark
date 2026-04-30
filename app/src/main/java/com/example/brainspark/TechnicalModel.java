package com.example.brainspark;

public class TechnicalModel {

    private String subject;
    private String question;
    private String answer;

    // 🔥 MUST REQUIRED for Firebase
    public TechnicalModel() {
    }

    public TechnicalModel(String subject, String question, String answer) {
        this.subject = subject;
        this.question = question;
        this.answer = answer;
    }

    public String getSubject() {
        return subject;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}