package com.githubtimemachine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "analytics_snapshots")
public class AnalyticsSnapshot extends BaseEntity {

    @Column(name = "architecture_score")
    private Integer architectureScore;

    @Column(name = "repository_health")
    private String repositoryHealth;

    @Column(name = "contribution_difficulty")
    private String contributionDifficulty;

    @Column(name = "ai_summary", length = 2000)
    private String aiSummary;

    @Column(name = "evolution_phases", length = 2000)
    private String evolutionPhases;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analyzed_user_id", nullable = false, unique = true)
    private AnalyzedUser analyzedUser;

    public AnalyticsSnapshot() {
    }

    public AnalyticsSnapshot(Integer architectureScore, String repositoryHealth, String contributionDifficulty, String aiSummary) {
        this.architectureScore = architectureScore;
        this.repositoryHealth = repositoryHealth;
        this.contributionDifficulty = contributionDifficulty;
        this.aiSummary = aiSummary;
    }

    // Getters and Setters
    public Integer getArchitectureScore() {
        return architectureScore;
    }

    public void setArchitectureScore(Integer architectureScore) {
        this.architectureScore = architectureScore;
    }

    public String getRepositoryHealth() {
        return repositoryHealth;
    }

    public void setRepositoryHealth(String repositoryHealth) {
        this.repositoryHealth = repositoryHealth;
    }

    public String getContributionDifficulty() {
        return contributionDifficulty;
    }

    public void setContributionDifficulty(String contributionDifficulty) {
        this.contributionDifficulty = contributionDifficulty;
    }

    public String getAiSummary() {
        return aiSummary;
    }

    public void setAiSummary(String aiSummary) {
        this.aiSummary = aiSummary;
    }

    public String getEvolutionPhases() {
        return evolutionPhases;
    }

    public void setEvolutionPhases(String evolutionPhases) {
        this.evolutionPhases = evolutionPhases;
    }

    public AnalyzedUser getAnalyzedUser() {
        return analyzedUser;
    }

    public void setAnalyzedUser(AnalyzedUser analyzedUser) {
        this.analyzedUser = analyzedUser;
    }
}
