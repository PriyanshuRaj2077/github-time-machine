package com.githubtimemachine.analytics.service;

public interface RepositoryAnalyzerService {
    int computeRepositoryActivityScore(String username);
    int computeProjectComplexityScore(String username);
}
