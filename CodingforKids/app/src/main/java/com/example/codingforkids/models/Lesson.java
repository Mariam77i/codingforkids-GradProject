package com.example.codingforkids.models;

public class Lesson {
    private String lesson_id;
    private String lesson_title;
    private String lesson_language;
    private String lesson_level;
    private String lesson_topic;
    private String lesson_desc;
    private String lesson_imageURL;
    private String lesson_soundURL;
    private String lesson_videoURL;
    private String lesson_userID;

    public Lesson(String lesson_id, String lesson_title, String lesson_language, String lesson_level, String lesson_topic,
                  String lesson_desc, String lesson_imageURL, String lesson_soundURL, String lesson_videoURL, String lesson_userID) {
        this.lesson_id = lesson_id;
        this.lesson_title = lesson_title;
        this.lesson_language = lesson_language;
        this.lesson_level = lesson_level;
        this.lesson_topic = lesson_topic;
        this.lesson_desc = lesson_desc;
        this.lesson_imageURL = lesson_imageURL;
        this.lesson_soundURL = lesson_soundURL;
        this.lesson_videoURL = lesson_videoURL;
        this.lesson_userID = lesson_userID;
    }

    public String getLesson_userID() {
        return lesson_userID;
    }

    public void setLesson_userID(String lesson_userID) {
        this.lesson_userID = lesson_userID;
    }

    public String getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getLesson_title() {
        return lesson_title;
    }

    public void setLesson_title(String lesson_title) {
        this.lesson_title = lesson_title;
    }

    public String getLesson_language() {
        return lesson_language;
    }

    public void setLesson_language(String lesson_language) {
        this.lesson_language = lesson_language;
    }

    public String getLesson_level() {
        return lesson_level;
    }

    public void setLesson_level(String lesson_level) {
        this.lesson_level = lesson_level;
    }

    public String getLesson_topic() {
        return lesson_topic;
    }

    public void setLesson_topic(String lesson_topic) {
        this.lesson_topic = lesson_topic;
    }

    public String getLesson_desc() {
        return lesson_desc;
    }

    public void setLesson_desc(String lesson_desc) {
        this.lesson_desc = lesson_desc;
    }

    public String getLesson_imageURL() {
        return lesson_imageURL;
    }

    public void setLesson_imageURL(String lesson_imageURL) {
        this.lesson_imageURL = lesson_imageURL;
    }

    public String getLesson_soundURL() {
        return lesson_soundURL;
    }

    public void setLesson_soundURL(String lesson_soundURL) {
        this.lesson_soundURL = lesson_soundURL;
    }

    public String getLesson_videoURL() {
        return lesson_videoURL;
    }

    public void setLesson_videoURL(String lesson_videoURL) {
        this.lesson_videoURL = lesson_videoURL;
    }
}
