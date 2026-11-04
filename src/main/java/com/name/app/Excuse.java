package com.name.app;

public class Excuse {
    private int id;
    private String text;
    private String ageGroup;

    public Excuse(int id, String text, String ageGroup) {
        this.id = id;
        this.text = text;
        this.ageGroup = ageGroup;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getAgeGroup() {
        return ageGroup;
    }
}