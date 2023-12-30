package com.example.pshepishnickproject;

public class Recipe {
    private String title;
    private String description;
    private int preparationDuration;
    private int difficulty;
    private String photoUrl;

    public Recipe(String title, String description, int preparationDuration, int difficulty, String photoUrl) {
        this.title = title;
        this.description = description;
        this.preparationDuration = preparationDuration;
        this.difficulty = difficulty;
        this.photoUrl = photoUrl;
    }

    public Recipe() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPreparationDuration() {
        return preparationDuration;
    }

    public void setPreparationDuration(int preparationDuration) {
        this.preparationDuration = preparationDuration;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", preparationDuration=" + preparationDuration +
                ", difficulty=" + difficulty +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}

