package com.example.codingforkids.models;

public class Exercise {
    private String exercise_id;
    private String lesson_title;
    private String lesson_id;
    private String exercise_lang;
    private String exercise_title;
    private String exercise_image1;
    private String exercise_image2;
    private String exercise_image3;
    private String exercise_answer;
    private String userID;

    public Exercise(String exercise_id, String exercise_answer, String lesson_title, String lesson_id, String exercise_lang,
                    String exercise_title, String exercise_image1, String exercise_image2, String exercise_image3, String userID) {
        this.exercise_id = exercise_id;
        this.lesson_title = lesson_title;
        this.lesson_id = lesson_id;
        this.exercise_answer = exercise_answer;
        this.exercise_lang = exercise_lang;
        this.exercise_title = exercise_title;
        this.exercise_image1 = exercise_image1;
        this.exercise_image2 = exercise_image2;
        this.exercise_image3 = exercise_image3;
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getExercise_answer() {
        return exercise_answer;
    }

    public void setExercise_answer(String exercise_answer) {
        this.exercise_answer = exercise_answer;
    }

    public String getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(String exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getLesson_title() {
        return lesson_title;
    }

    public void setLesson_title(String lesson_title) {
        this.lesson_title = lesson_title;
    }

    public String getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getExercise_lang() {
        return exercise_lang;
    }

    public void setExercise_lang(String exercise_lang) {
        this.exercise_lang = exercise_lang;
    }

    public String getExercise_title() {
        return exercise_title;
    }

    public void setExercise_title(String exercise_title) {
        this.exercise_title = exercise_title;
    }

    public String getExercise_image1() {
        return exercise_image1;
    }

    public void setExercise_image1(String exercise_image1) {
        this.exercise_image1 = exercise_image1;
    }

    public String getExercise_image2() {
        return exercise_image2;
    }

    public void setExercise_image2(String exercise_image2) {
        this.exercise_image2 = exercise_image2;
    }

    public String getExercise_image3() {
        return exercise_image3;
    }

    public void setExercise_image3(String exercise_image3) {
        this.exercise_image3 = exercise_image3;
    }
}
