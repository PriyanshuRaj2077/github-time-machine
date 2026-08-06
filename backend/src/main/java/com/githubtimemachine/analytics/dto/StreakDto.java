package com.githubtimemachine.analytics.dto;

public class StreakDto {

    private int currentStreakDays;
    private int longestStreakDays;
    private int totalContributionDays;
    private boolean activeToday;
    private String longestHiatusPeriod;

    public StreakDto() {
    }

    public StreakDto(int currentStreakDays, int longestStreakDays, int totalContributionDays, boolean activeToday, String longestHiatusPeriod) {
        this.currentStreakDays = currentStreakDays;
        this.longestStreakDays = longestStreakDays;
        this.totalContributionDays = totalContributionDays;
        this.activeToday = activeToday;
        this.longestHiatusPeriod = longestHiatusPeriod;
    }

    public int getCurrentStreakDays() {
        return currentStreakDays;
    }

    public void setCurrentStreakDays(int currentStreakDays) {
        this.currentStreakDays = currentStreakDays;
    }

    public int getLongestStreakDays() {
        return longestStreakDays;
    }

    public void setLongestStreakDays(int longestStreakDays) {
        this.longestStreakDays = longestStreakDays;
    }

    public int getTotalContributionDays() {
        return totalContributionDays;
    }

    public void setTotalContributionDays(int totalContributionDays) {
        this.totalContributionDays = totalContributionDays;
    }

    public boolean isActiveToday() {
        return activeToday;
    }

    public void setActiveToday(boolean activeToday) {
        this.activeToday = activeToday;
    }

    public String getLongestHiatusPeriod() {
        return longestHiatusPeriod;
    }

    public void setLongestHiatusPeriod(String longestHiatusPeriod) {
        this.longestHiatusPeriod = longestHiatusPeriod;
    }
}
