package com.githubtimemachine.github.dto;

import java.time.LocalDateTime;

public class GitHubUserProfileDto {

    private String username;
    private String name;
    private String avatarUrl;
    private String bio;
    private LocalDateTime accountCreatedAt;
    private int followersCount;
    private int publicReposCount;
    private int yearsCoding;
    private int monthsCoding;
    private int daysCoding;

    public GitHubUserProfileDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getAccountCreatedAt() {
        return accountCreatedAt;
    }

    public void setAccountCreatedAt(LocalDateTime accountCreatedAt) {
        this.accountCreatedAt = accountCreatedAt;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getPublicReposCount() {
        return publicReposCount;
    }

    public void setPublicReposCount(int publicReposCount) {
        this.publicReposCount = publicReposCount;
    }

    public int getYearsCoding() {
        return yearsCoding;
    }

    public void setYearsCoding(int yearsCoding) {
        this.yearsCoding = yearsCoding;
    }

    public int getMonthsCoding() {
        return monthsCoding;
    }

    public void setMonthsCoding(int monthsCoding) {
        this.monthsCoding = monthsCoding;
    }

    public int getDaysCoding() {
        return daysCoding;
    }

    public void setDaysCoding(int daysCoding) {
        this.daysCoding = daysCoding;
    }
}
