package com.githubtimemachine.ai.prompt;

import com.githubtimemachine.analytics.dto.AnalyticsOverviewDto;
import com.githubtimemachine.analytics.dto.DeveloperStatsDto;
import com.githubtimemachine.analytics.dto.LanguageDistributionDto;
import com.githubtimemachine.analytics.dto.StreakDto;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildPersonalityPrompt(AnalyticsOverviewDto overview) {
        LanguageDistributionDto lang = overview.getLanguageDistribution();
        StreakDto streak = overview.getStreak();
        DeveloperStatsDto stats = overview.getStats();

        String skills = String.join(", ", lang.getDetectedSkills());
        return String.format(
                PromptTemplates.DEVELOPER_PERSONALITY,
                lang.getFavoriteLanguage(),
                streak.getCurrentStreakDays(),
                stats.getGrowthVelocityPercent(),
                skills
        );
    }

    public String buildHealthPrompt(DeveloperStatsDto stats) {
        return String.format(
                PromptTemplates.REPOSITORY_HEALTH,
                stats.getRepositoryActivityScore(),
                stats.getProjectComplexityScore()
        );
    }

    public String buildPredictionPrompt(DeveloperStatsDto stats) {
        return String.format(
                PromptTemplates.PROJECT_COMPLETION_PREDICTION,
                stats.getRepositoryActivityScore(),
                stats.getGrowthVelocityPercent()
        );
    }

    public String buildDnaPrompt(LanguageDistributionDto lang) {
        String skills = String.join(", ", lang.getDetectedSkills());
        return String.format(
                PromptTemplates.DEVELOPER_DNA,
                lang.getFavoriteLanguage(),
                skills
        );
    }
}
