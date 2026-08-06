package com.githubtimemachine.ai.dto;

import java.util.List;

public class AiInsightsResponseDto {

    private String username;
    private String developerPersonality;
    private List<String> developerDna;
    private String repositoryHealthSummary;
    private String projectCompletionPrediction;

    public AiInsightsResponseDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeveloperPersonality() {
        return developerPersonality;
    }

    public void setDeveloperPersonality(String developerPersonality) {
        this.developerPersonality = developerPersonality;
    }

    public List<String> getDeveloperDna() {
        return developerDna;
    }

    public void setDeveloperDna(List<String> developerDna) {
        this.developerDna = developerDna;
    }

    public String getRepositoryHealthSummary() {
        return repositoryHealthSummary;
    }

    public void setRepositoryHealthSummary(String repositoryHealthSummary) {
        this.repositoryHealthSummary = repositoryHealthSummary;
    }

    public String getProjectCompletionPrediction() {
        return projectCompletionPrediction;
    }

    public void setProjectCompletionPrediction(String projectCompletionPrediction) {
        this.projectCompletionPrediction = projectCompletionPrediction;
    }
}
