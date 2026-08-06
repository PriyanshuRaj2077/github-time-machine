package com.githubtimemachine.github.dto;

public class GitHubCommitEventDto {

    private String date;
    private String text;
    private int commitCount;

    public GitHubCommitEventDto() {
    }

    public GitHubCommitEventDto(String date, String text, int commitCount) {
        this.date = date;
        this.text = text;
        this.commitCount = commitCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(int commitCount) {
        this.commitCount = commitCount;
    }
}
