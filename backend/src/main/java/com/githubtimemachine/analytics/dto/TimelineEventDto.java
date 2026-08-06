package com.githubtimemachine.analytics.dto;

public class TimelineEventDto {

    private String date;
    private String title;
    private String description;
    private String category;

    public TimelineEventDto() {
    }

    public TimelineEventDto(String date, String title, String description, String category) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
