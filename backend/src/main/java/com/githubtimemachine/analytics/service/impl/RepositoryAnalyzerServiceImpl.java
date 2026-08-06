package com.githubtimemachine.analytics.service.impl;

import com.githubtimemachine.analytics.service.RepositoryAnalyzerService;
import com.githubtimemachine.github.service.GitHubService;
import org.springframework.stereotype.Service;

@Service
public class RepositoryAnalyzerServiceImpl implements RepositoryAnalyzerService {

    private final GitHubService gitHubService;

    public RepositoryAnalyzerServiceImpl(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @Override
    public int computeRepositoryActivityScore(String username) {
        int stars = gitHubService.fetchTotalStars(username);
        int forks = gitHubService.fetchTotalForks(username);
        return Math.min(100, 70 + (stars / 100) + (forks / 50));
    }

    @Override
    public int computeProjectComplexityScore(String username) {
        int langs = gitHubService.fetchLanguages(username).size();
        return Math.min(100, 80 + (langs * 3));
    }
}
