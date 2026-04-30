package com.example.brainspark;

public class TopicApptitudeModel {
    public String question;
    public String solution;

    // Required for Firebase
    public void TopicApptitudeModel() {}

    public void TopicApptitudeModel(String question, String solution) {
        this.question = question;
        this.solution = solution;
    }
}
