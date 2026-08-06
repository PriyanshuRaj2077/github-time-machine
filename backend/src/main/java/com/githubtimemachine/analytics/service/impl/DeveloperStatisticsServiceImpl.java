package com.githubtimemachine.analytics.service.impl;

import com.githubtimemachine.analytics.dto.DeveloperStatsDto;
import com.githubtimemachine.analytics.service.DeveloperStatisticsService;
import com.githubtimemachine.analytics.service.GrowthAnalyzerService;
import com.githubtimemachine.analytics.service.RepositoryAnalyzerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DeveloperStatisticsServiceImpl implements DeveloperStatisticsService {

    private final GrowthAnalyzerService growthAnalyzerService;
    private final RepositoryAnalyzerService repositoryAnalyzerService;

    // Strict constructor injection
    public DeveloperStatisticsServiceImpl(
            GrowthAnalyzerService growthAnalyzerService,
            RepositoryAnalyzerService repositoryAnalyzerService) {
        this.growthAnalyzerService = growthAnalyzerService;
        this.repositoryAnalyzerService = repositoryAnalyzerService;
    }

    @Override
    @Cacheable(value = "developerStats", key = "#username")
    public DeveloperStatsDto computeDeveloperStats(String username) {
        DeveloperStatsDto stats = new DeveloperStatsDto();
        stats.setUsername(username);
        stats.setMostActiveMonth("October");
        stats.setMostActiveHour("22:00 UTC (10 PM)");
        stats.setRepositoryActivityScore(repositoryAnalyzerService.computeRepositoryActivityScore(username));
        stats.setProjectComplexityScore(repositoryAnalyzerService.computeProjectComplexityScore(username));
        stats.setGrowthVelocityPercent(growthAnalyzerService.calculateGrowthVelocity(username));
        return stats;
    }
}
