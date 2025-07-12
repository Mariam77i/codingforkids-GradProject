package com.example.codingforkids.models;

public class LessonRequest {
    private String request_id;
    private String lesson_title;
    private String request_desc;
    private String request_lang;
    private String request_topic;
    private String request_type;
    private String request_status;

    public LessonRequest(String request_id, String lesson_title, String request_desc, String request_lang, String request_topic,
                         String request_type, String request_status) {
        this.request_id = request_id;
        this.lesson_title = lesson_title;
        this.request_desc = request_desc;
        this.request_lang = request_lang;
        this.request_topic = request_topic;
        this.request_type = request_type;
        this.request_status = request_status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getLesson_title() {
        return lesson_title;
    }

    public void setLesson_title(String lesson_title) {
        this.lesson_title = lesson_title;
    }

    public String getRequest_desc() {
        return request_desc;
    }

    public void setRequest_desc(String request_desc) {
        this.request_desc = request_desc;
    }

    public String getRequest_lang() {
        return request_lang;
    }

    public void setRequest_lang(String request_lang) {
        this.request_lang = request_lang;
    }

    public String getRequest_topic() {
        return request_topic;
    }

    public void setRequest_topic(String request_topic) {
        this.request_topic = request_topic;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }
}
