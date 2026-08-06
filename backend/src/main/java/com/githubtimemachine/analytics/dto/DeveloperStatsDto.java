package com.githubtimemachine.analytics.dto;

public class DeveloperStatsDto {

    private String username;
    private String mostActiveMonth;
    private String mostActiveHour;
    private int repositoryActivityScore;
    private int projectComplexityScore;
    private double growthVelocityPercent;

    public DeveloperStatsDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMostActiveMonth() {
        return mostActiveMonth;
    }

    public void setMostActiveMonth(String mostActiveMonth) {
        this.mostActiveMonth = mostActiveMonth;
    }

    public String getMostActiveHour() {
        return mostActiveHour;
    }

    public void setMostActiveHour(String mostActiveHour) {
        this.mostActiveHour = mostActiveHour;
    }

    public int getRepositoryActivityScore() {
        return repositoryActivityScore;
    }

    public void setRepositoryActivityScore(int repositoryActivityScore) {
        this.repositoryActivityScore = repositoryActivityScore;
    }

    public int getProjectComplexityScore() {
        return projectComplexityScore;
    }

    public void setProjectComplexityScore(int projectComplexityScore) {
        this.projectComplexityScore = projectComplexityScore;
    }

    public double getGrowthVelocityPercent() {
        return growthVelocityPercent;
    }

    public void setGrowthVelocityPercent(double growthVelocityPercent) {
        this.growthVelocityPercent = growthVelocityPercent;
    }
}
