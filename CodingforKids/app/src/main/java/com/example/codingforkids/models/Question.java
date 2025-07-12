package com.example.codingforkids.models;

public class Question {
    String qID;
    String qTitle;
    String qOption1;
    String qOption2;
    String qOption3;
    String qOption4;
    String qAnswer;

    public Question(String qID, String qTitle, String qOption1, String qOption2, String qOption3, String qOption4, String qAnswer) {
        this.qID = qID;
        this.qTitle = qTitle;
        this.qOption1 = qOption1;
        this.qOption2 = qOption2;
        this.qOption3 = qOption3;
        this.qOption4 = qOption4;
        this.qAnswer = qAnswer;
    }

    public String getqID() {
        return qID;
    }

    public void setqID(String qID) {
        this.qID = qID;
    }

    public String getqTitle() {
        return qTitle;
    }

    public void setqTitle(String qTitle) {
        this.qTitle = qTitle;
    }

    public String getqOption1() {
        return qOption1;
    }

    public void setqOption1(String qOption1) {
        this.qOption1 = qOption1;
    }

    public String getqOption2() {
        return qOption2;
    }

    public void setqOption2(String qOption2) {
        this.qOption2 = qOption2;
    }

    public String getqOption3() {
        return qOption3;
    }

    public void setqOption3(String qOption3) {
        this.qOption3 = qOption3;
    }

    public String getqAnswer() {
        return qAnswer;
    }

    public String getqOption4() {
        return qOption4;
    }

    public void setqOption4(String qOption4) {
        this.qOption4 = qOption4;
    }

    public void setqAnswer(String qAnswer) {
        this.qAnswer = qAnswer;
    }
}
