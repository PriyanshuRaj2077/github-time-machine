package com.githubtimemachine.github.dto;

import java.util.List;

public class GitHubRepositoryDto {

    private String name;
    private String fullName;
    private String owner;
    private String description;
    private String primaryLanguage;
    private List<String> languages;
    private int starsCount;
    private int forksCount;
    private String codebaseAge;

    public GitHubRepositoryDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(String primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public String getCodebaseAge() {
        return codebaseAge;
    }

    public void setCodebaseAge(String codebaseAge) {
        this.codebaseAge = codebaseAge;
    }
}
