package com.example.brainspark;

public class QuestionModel {
    private String Question;
    private  String Option1;
    private  String Option2;
    private String Option3;
    private String Option4;
    private String Answer;
    public QuestionModel(){}
    public QuestionModel(String Question, String Option1, String Option2, String Option3, String Option4, String Answer) {
       this.Question = Question;
        this.Option1 = Option1;
        this.Option2 = Option2;
       this.Option3 = Option3;
        this.Option4 = Option4;
        this.Answer = Answer;
    }
    // 🔹 Getters
    public void setQuestion(String Question) { this.Question = Question; }
    public void setOption1(String Option1) { this.Option1 = Option1; }

    public void setOption2(String Option2) { this.Option2 = Option2; }
    public void setOption3(String Option3) { this.Option3 = Option3; }
    public void setOption4(String Option4) { this.Option4 = Option4; }
    public void setAnswer(String Answer){
        this.Answer=Answer;
    }
    public String getQuestion() {
        return Question;
    }
    public String getOption1() {
        return Option1;
    }

    public String getOption2() {
        return Option2;
    }
    public String getOption3() {
        return Option3;
    }

    public String getOption4() {
        return Option4;
    }

    public String getAnswer() {
        return Answer;
    }


}

