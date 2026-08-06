package com.githubtimemachine.dto.response;

import java.util.UUID;

public class AnalyzedUserResponseDto {

    private UUID id;
    private String username;
    private String name;
    private String avatarUrl;
    private String bio;
    private Integer publicReposCount;
    private Integer followersCount;
    private Integer yearsCoding;
    private Integer monthsCoding;
    private Integer daysCoding;

    public AnalyzedUserResponseDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Integer getPublicReposCount() {
        return publicReposCount;
    }

    public void setPublicReposCount(Integer publicReposCount) {
        this.publicReposCount = publicReposCount;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getYearsCoding() {
        return yearsCoding;
    }

    public void setYearsCoding(Integer yearsCoding) {
        this.yearsCoding = yearsCoding;
    }

    public Integer getMonthsCoding() {
        return monthsCoding;
    }

    public void setMonthsCoding(Integer monthsCoding) {
        this.monthsCoding = monthsCoding;
    }

    public Integer getDaysCoding() {
        return daysCoding;
    }

    public void setDaysCoding(Integer daysCoding) {
        this.daysCoding = daysCoding;
    }
}
