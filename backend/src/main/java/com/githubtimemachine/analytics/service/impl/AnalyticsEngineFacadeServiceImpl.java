package com.githubtimemachine.analytics.service.impl;

import com.githubtimemachine.analytics.dto.AnalyticsOverviewDto;
import com.githubtimemachine.analytics.dto.DeveloperStatsDto;
import com.githubtimemachine.analytics.dto.LanguageDistributionDto;
import com.githubtimemachine.analytics.dto.StreakDto;
import com.githubtimemachine.analytics.dto.TimelineDto;
import com.githubtimemachine.analytics.service.AnalyticsEngineFacadeService;
import com.githubtimemachine.analytics.service.CommitStreakAnalyzerService;
import com.githubtimemachine.analytics.service.DeveloperStatisticsService;
import com.githubtimemachine.analytics.service.LanguageAnalyzerService;
import com.githubtimemachine.analytics.service.TimelineGeneratorService;
import com.githubtimemachine.entity.AnalyticsSnapshot;
import com.githubtimemachine.entity.AnalyzedUser;
import com.githubtimemachine.repository.AnalyticsSnapshotRepository;
import com.githubtimemachine.repository.AnalyzedUserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AnalyticsEngineFacadeServiceImpl implements AnalyticsEngineFacadeService {

    private final CommitStreakAnalyzerService streakAnalyzerService;
    private final LanguageAnalyzerService languageAnalyzerService;
    private final DeveloperStatisticsService developerStatisticsService;
    private final TimelineGeneratorService timelineGeneratorService;
    private final AnalyzedUserRepository userRepository;
    private final AnalyticsSnapshotRepository analyticsSnapshotRepository;

    // Strict constructor injection ONLY (No field @Autowired)
    public AnalyticsEngineFacadeServiceImpl(
            CommitStreakAnalyzerService streakAnalyzerService,
            LanguageAnalyzerService languageAnalyzerService,
            DeveloperStatisticsService developerStatisticsService,
            TimelineGeneratorService timelineGeneratorService,
            AnalyzedUserRepository userRepository,
            AnalyticsSnapshotRepository analyticsSnapshotRepository) {
        this.streakAnalyzerService = streakAnalyzerService;
        this.languageAnalyzerService = languageAnalyzerService;
        this.developerStatisticsService = developerStatisticsService;
        this.timelineGeneratorService = timelineGeneratorService;
        this.userRepository = userRepository;
        this.analyticsSnapshotRepository = analyticsSnapshotRepository;
    }

    @Override
    @Transactional
    @Cacheable(value = "analyticsOverview", key = "#username")
    public AnalyticsOverviewDto getAnalyticsOverview(String username) {
        StreakDto streak = streakAnalyzerService.analyzeStreak(username);
        LanguageDistributionDto langDist = languageAnalyzerService.analyzeLanguages(username);
        DeveloperStatsDto stats = developerStatisticsService.computeDeveloperStats(username);
        TimelineDto timeline = timelineGeneratorService.generateTimeline(username);

        AnalyticsOverviewDto overview = new AnalyticsOverviewDto(
                username,
                streak,
                langDist,
                stats,
                timeline
        );

        // Persist structured analytics snapshot to PostgreSQL
        persistSnapshot(username, stats, langDist);

        return overview;
    }

    private void persistSnapshot(String username, DeveloperStatsDto stats, LanguageDistributionDto langDist) {
        Optional<AnalyzedUser> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            AnalyzedUser user = userOpt.get();
            Optional<AnalyticsSnapshot> existing = analyticsSnapshotRepository.findByAnalyzedUserUsername(username);

            AnalyticsSnapshot snapshot = existing.orElseGet(() -> {
                AnalyticsSnapshot s = new AnalyticsSnapshot();
                s.setAnalyzedUser(user);
                return s;
            });

            snapshot.setArchitectureScore(stats.getRepositoryActivityScore());
            snapshot.setRepositoryHealth("EXCELLENT (" + stats.getProjectComplexityScore() + "%)");
            snapshot.setContributionDifficulty("MODERATE");
            snapshot.setAiSummary("System metrics computed deterministically. Primary Language: " + langDist.getFavoriteLanguage());
            snapshot.setEvolutionPhases("Growth velocity: +" + stats.getGrowthVelocityPercent() + "% YoY.");

            analyticsSnapshotRepository.save(snapshot);
        }
    }
}
