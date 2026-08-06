package com.githubtimemachine.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "analyzed_users", indexes = {
    @Index(name = "idx_analyzed_user_username", columnList = "username", unique = true),
    @Index(name = "idx_analyzed_user_created_at", columnList = "created_at")
})
public class AnalyzedUser extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "github_id")
    private Long githubId;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar_url", length = 512)
    private String avatarUrl;

    @Column(name = "bio", length = 1000)
    private String bio;

    @Column(name = "public_repos_count")
    private Integer publicReposCount = 0;

    @Column(name = "followers_count")
    private Integer followersCount = 0;

    @Column(name = "years_coding")
    private Integer yearsCoding = 0;

    @Column(name = "months_coding")
    private Integer monthsCoding = 0;

    @Column(name = "days_coding")
    private Integer daysCoding = 0;

    @OneToMany(mappedBy = "analyzedUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RepositorySnapshot> repositories = new ArrayList<>();

    @OneToOne(mappedBy = "analyzedUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AnalyticsSnapshot analyticsSnapshot;

    public AnalyzedUser() {
    }

    public AnalyzedUser(String username, String name, String avatarUrl, String bio) {
        this.username = username;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
    }

    public void addRepository(RepositorySnapshot repository) {
        repositories.add(repository);
        repository.setAnalyzedUser(this);
    }

    public void removeRepository(RepositorySnapshot repository) {
        repositories.remove(repository);
        repository.setAnalyzedUser(null);
    }

    public void setAnalyticsSnapshot(AnalyticsSnapshot analyticsSnapshot) {
        if (analyticsSnapshot == null) {
            if (this.analyticsSnapshot != null) {
                this.analyticsSnapshot.setAnalyzedUser(null);
            }
        } else {
            analyticsSnapshot.setAnalyzedUser(this);
        }
        this.analyticsSnapshot = analyticsSnapshot;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getGithubId() {
        return githubId;
    }

    public void setGithubId(Long githubId) {
        this.githubId = githubId;
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

    public List<RepositorySnapshot> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositorySnapshot> repositories) {
        this.repositories = repositories;
    }

    public AnalyticsSnapshot getAnalyticsSnapshot() {
        return analyticsSnapshot;
    }
}
