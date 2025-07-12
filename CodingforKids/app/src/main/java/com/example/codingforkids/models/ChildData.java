package com.example.codingforkids.models;

public class ChildData {
    private String childID;
    private String childName;
    private String childEmail;
    private String childPass;
    private String childUsername;
    private String childAge;
    private String childEdu;
    private String childGender;
    private String childParentID;
    private String childLevel;
    private String childScore;

    public ChildData(String childID, String childName, String childEmail, String childPass, String childUsername, String childAge, String childEdu, String childGender, String childParentID, String childLevel, String childScore) {
        this.childID = childID;
        this.childName = childName;
        this.childEmail = childEmail;
        this.childPass = childPass;
        this.childUsername = childUsername;
        this.childAge = childAge;
        this.childEdu = childEdu;
        this.childGender = childGender;
        this.childParentID = childParentID;
        this.childLevel = childLevel;
        this.childScore = childScore;
    }

    public String getChildID() {
        return childID;
    }

    public void setChildID(String childID) {
        this.childID = childID;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildEmail() {
        return childEmail;
    }

    public void setChildEmail(String childEmail) {
        this.childEmail = childEmail;
    }

    public String getChildPass() {
        return childPass;
    }

    public void setChildPass(String childPass) {
        this.childPass = childPass;
    }

    public String getChildUsername() {
        return childUsername;
    }

    public void setChildUsername(String childUsername) {
        this.childUsername = childUsername;
    }

    public String getChildAge() {
        return childAge;
    }

    public void setChildAge(String childAge) {
        this.childAge = childAge;
    }

    public String getChildEdu() {
        return childEdu;
    }

    public void setChildEdu(String childEdu) {
        this.childEdu = childEdu;
    }

    public String getChildGender() {
        return childGender;
    }

    public void setChildGender(String childGender) {
        this.childGender = childGender;
    }

    public String getChildParentID() {
        return childParentID;
    }

    public void setChildParentID(String childParentID) {
        this.childParentID = childParentID;
    }

    public String getChildLevel() {
        return childLevel;
    }

    public void setChildLevel(String childLevel) {
        this.childLevel = childLevel;
    }

    public String getChildScore() {
        return childScore;
    }

    public void setChildScore(String childScore) {
        this.childScore = childScore;
    }
}
