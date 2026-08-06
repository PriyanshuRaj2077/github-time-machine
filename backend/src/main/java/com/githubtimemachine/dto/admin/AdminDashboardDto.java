package com.githubtimemachine.dto.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AdminDashboardDto {

    private long totalUsers;
    private long totalLogins;
    private long newUsers24h;
    private long dailyActiveUsers;
    private long totalAnalyses;
    private long githubSearches;
    private long repositorySearches;
    private long aiRequests;
    private long apiUsageCount;
    private String databaseStatus;
    private String applicationUptime;
    private List<String> recentErrors;
    private List<Map<String, Object>> mostSearchedUsers;
    private List<Map<String, Object>> mostSearchedRepositories;

    public AdminDashboardDto() {
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalLogins() {
        return totalLogins;
    }

    public void setTotalLogins(long totalLogins) {
        this.totalLogins = totalLogins;
    }

    public long getNewUsers24h() {
        return newUsers24h;
    }

    public void setNewUsers24h(long newUsers24h) {
        this.newUsers24h = newUsers24h;
    }

    public long getDailyActiveUsers() {
        return dailyActiveUsers;
    }

    public void setDailyActiveUsers(long dailyActiveUsers) {
        this.dailyActiveUsers = dailyActiveUsers;
    }

    public long getTotalAnalyses() {
        return totalAnalyses;
    }

    public void setTotalAnalyses(long totalAnalyses) {
        this.totalAnalyses = totalAnalyses;
    }

    public long getGithubSearches() {
        return githubSearches;
    }

    public void setGithubSearches(long githubSearches) {
        this.githubSearches = githubSearches;
    }

    public long getRepositorySearches() {
        return repositorySearches;
    }

    public void setRepositorySearches(long repositorySearches) {
        this.repositorySearches = repositorySearches;
    }

    public long getAiRequests() {
        return aiRequests;
    }

    public void setAiRequests(long aiRequests) {
        this.aiRequests = aiRequests;
    }

    public long getApiUsageCount() {
        return apiUsageCount;
    }

    public void setApiUsageCount(long apiUsageCount) {
        this.apiUsageCount = apiUsageCount;
    }

    public String getDatabaseStatus() {
        return databaseStatus;
    }

    public void setDatabaseStatus(String databaseStatus) {
        this.databaseStatus = databaseStatus;
    }

    public String getApplicationUptime() {
        return applicationUptime;
    }

    public void setApplicationUptime(String applicationUptime) {
        this.applicationUptime = applicationUptime;
    }

    public List<String> getRecentErrors() {
        return recentErrors;
    }

    public void setRecentErrors(List<String> recentErrors) {
        this.recentErrors = recentErrors;
    }

    public List<Map<String, Object>> getMostSearchedUsers() {
        return mostSearchedUsers;
    }

    public void setMostSearchedUsers(List<Map<String, Object>> mostSearchedUsers) {
        this.mostSearchedUsers = mostSearchedUsers;
    }

    public List<Map<String, Object>> getMostSearchedRepositories() {
        return mostSearchedRepositories;
    }

    public void setMostSearchedRepositories(List<Map<String, Object>> mostSearchedRepositories) {
        this.mostSearchedRepositories = mostSearchedRepositories;
    }
}
