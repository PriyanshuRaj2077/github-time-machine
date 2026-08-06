package com.githubtimemachine.ai.dto;

public class AiWrappedResponseDto {

    private String username;
    private String growthSummary;
    private String repositorySummary;
    private String wrappedNarrative;

    public AiWrappedResponseDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGrowthSummary() {
        return growthSummary;
    }

    public void setGrowthSummary(String growthSummary) {
        this.growthSummary = growthSummary;
    }

    public String getRepositorySummary() {
        return repositorySummary;
    }

    public void setRepositorySummary(String repositorySummary) {
        this.repositorySummary = repositorySummary;
    }

    public String getWrappedNarrative() {
        return wrappedNarrative;
    }

    public void setWrappedNarrative(String wrappedNarrative) {
        this.wrappedNarrative = wrappedNarrative;
    }
}
