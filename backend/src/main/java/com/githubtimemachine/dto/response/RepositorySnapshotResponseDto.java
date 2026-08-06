package com.githubtimemachine.dto.response;

import java.util.UUID;

public class RepositorySnapshotResponseDto {

    private UUID id;
    private String repoName;
    private String fullName;
    private String owner;
    private String description;
    private String primaryLanguage;
    private Integer starsCount;
    private Integer forksCount;
    private String codebaseAge;

    public RepositorySnapshotResponseDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
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

    public Integer getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(Integer starsCount) {
        this.starsCount = starsCount;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

    public String getCodebaseAge() {
        return codebaseAge;
    }

    public void setCodebaseAge(String codebaseAge) {
        this.codebaseAge = codebaseAge;
    }
}
