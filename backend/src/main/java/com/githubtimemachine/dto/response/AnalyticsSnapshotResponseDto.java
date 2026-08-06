package com.githubtimemachine.dto.response;

import java.util.UUID;

public class AnalyticsSnapshotResponseDto {

    private UUID id;
    private Integer architectureScore;
    private String repositoryHealth;
    private String contributionDifficulty;
    private String aiSummary;
    private String evolutionPhases;

    public AnalyticsSnapshotResponseDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
}
