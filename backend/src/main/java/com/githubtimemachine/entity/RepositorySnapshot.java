package com.githubtimemachine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "repository_snapshots", indexes = {
    @Index(name = "idx_repo_snapshot_user", columnList = "analyzed_user_id"),
    @Index(name = "idx_repo_snapshot_lang", columnList = "primary_language")
})
public class RepositorySnapshot extends BaseEntity {

    @Column(name = "repo_name", nullable = false)
    private String repoName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "primary_language")
    private String primaryLanguage;

    @Column(name = "stars_count")
    private Integer starsCount = 0;

    @Column(name = "forks_count")
    private Integer forksCount = 0;

    @Column(name = "codebase_age")
    private String codebaseAge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analyzed_user_id", nullable = false)
    private AnalyzedUser analyzedUser;

    public RepositorySnapshot() {
    }

    public RepositorySnapshot(String repoName, String fullName, String owner, String description, String primaryLanguage) {
        this.repoName = repoName;
        this.fullName = fullName;
        this.owner = owner;
        this.description = description;
        this.primaryLanguage = primaryLanguage;
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

    public AnalyzedUser getAnalyzedUser() {
        return analyzedUser;
    }

    public void setAnalyzedUser(AnalyzedUser analyzedUser) {
        this.analyzedUser = analyzedUser;
    }
}
