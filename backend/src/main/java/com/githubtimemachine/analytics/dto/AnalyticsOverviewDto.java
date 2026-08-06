package com.githubtimemachine.analytics.dto;

public class AnalyticsOverviewDto {

    private String username;
    private StreakDto streak;
    private LanguageDistributionDto languageDistribution;
    private DeveloperStatsDto stats;
    private TimelineDto timeline;

    public AnalyticsOverviewDto() {
    }

    public AnalyticsOverviewDto(String username, StreakDto streak, LanguageDistributionDto languageDistribution, DeveloperStatsDto stats, TimelineDto timeline) {
        this.username = username;
        this.streak = streak;
        this.languageDistribution = languageDistribution;
        this.stats = stats;
        this.timeline = timeline;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public StreakDto getStreak() {
        return streak;
    }

    public void setStreak(StreakDto streak) {
        this.streak = streak;
    }

    public LanguageDistributionDto getLanguageDistribution() {
        return languageDistribution;
    }

    public void setLanguageDistribution(LanguageDistributionDto languageDistribution) {
        this.languageDistribution = languageDistribution;
    }

    public DeveloperStatsDto getStats() {
        return stats;
    }

    public void setStats(DeveloperStatsDto stats) {
        this.stats = stats;
    }

    public TimelineDto getTimeline() {
        return timeline;
    }

    public void setTimeline(TimelineDto timeline) {
        this.timeline = timeline;
    }
}
