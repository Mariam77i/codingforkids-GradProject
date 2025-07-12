package com.example.codingforkids.models;

public class Parent {
    private String parentID;
    private String parentName;
    private String parentEmail;
    private String parentPass;
    private String parentUsername;

    public Parent(String parentID, String parentName, String parentEmail, String parentUsername, String parentPass) {
        this.parentID = parentID;
        this.parentName = parentName;
        this.parentEmail = parentEmail;
        this.parentPass = parentPass;
        this.parentUsername = parentUsername;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getParentPass() {
        return parentPass;
    }

    public void setParentPass(String parentPass) {
        this.parentPass = parentPass;
    }

    public String getParentUsername() {
        return parentUsername;
    }

    public void setParentUsername(String parentUsername) {
        this.parentUsername = parentUsername;
    }
}
