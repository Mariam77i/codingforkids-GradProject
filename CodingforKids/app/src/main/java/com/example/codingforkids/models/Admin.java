package com.example.codingforkids.models;

public class Admin {
    private String adminID;
    private String adminName;
    private String adminEmail;
    private String adminPass;
    private String adminUsername;

    public Admin(String adminID, String adminName, String adminEmail, String adminUsername, String adminPass) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminUsername = adminUsername;
        this.adminPass = adminPass;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

}
