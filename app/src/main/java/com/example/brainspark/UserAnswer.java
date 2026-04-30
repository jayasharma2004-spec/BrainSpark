package com.example.brainspark;

public class UserAnswer {
    public String original;
    public String spoken;
    public boolean correct;

    public UserAnswer(String o, String s, boolean c) {
        original = o;
        spoken = s;
        correct = c;
    }
}
