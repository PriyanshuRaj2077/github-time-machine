package com.githubtimemachine.ai.service.impl;

import com.githubtimemachine.ai.client.AiClient;
import com.githubtimemachine.ai.dto.AiInsightsResponseDto;
import com.githubtimemachine.ai.dto.AiReplayResponseDto;
import com.githubtimemachine.ai.dto.AiWrappedResponseDto;
import com.githubtimemachine.ai.prompt.PromptBuilder;
import com.githubtimemachine.ai.service.AIService;
import com.githubtimemachine.analytics.dto.AnalyticsOverviewDto;
import com.githubtimemachine.analytics.dto.DeveloperStatsDto;
import com.githubtimemachine.analytics.dto.LanguageDistributionDto;
import com.githubtimemachine.analytics.dto.TimelineEventDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIServiceImpl implements AIService {

    private final AiClient aiClient;
    private final PromptBuilder promptBuilder;

    // Strict constructor injection ONLY (No @Autowired field injection)
    public AIServiceImpl(AiClient aiClient, PromptBuilder promptBuilder) {
        this.aiClient = aiClient;
        this.promptBuilder = promptBuilder;
    }

    @Override
    public AiInsightsResponseDto generateInsights(AnalyticsOverviewDto analyticsOverview) {
        DeveloperStatsDto stats = analyticsOverview.getStats();
        LanguageDistributionDto lang = analyticsOverview.getLanguageDistribution();

        AiInsightsResponseDto dto = new AiInsightsResponseDto();
        dto.setUsername(analyticsOverview.getUsername());

        // Construct Prompts from Analytics DTOs
        String personalityPrompt = promptBuilder.buildPersonalityPrompt(analyticsOverview);
        String healthPrompt = promptBuilder.buildHealthPrompt(stats);
        String predictionPrompt = promptBuilder.buildPredictionPrompt(stats);
        String dnaPrompt = promptBuilder.buildDnaPrompt(lang);

        // Attempt LLM generation or graceful deterministic fallback
        String llmPersonality = aiClient.generateCompletion(personalityPrompt);
        dto.setDeveloperPersonality(llmPersonality != null ? llmPersonality :
                "Pragmatic System Architect with high commit velocity in " + lang.getFavoriteLanguage() + " and modular engineering focus.");

        dto.setDeveloperDna(List.of(
                lang.getFavoriteLanguage(),
                "Distributed Systems",
                "High Velocity"
        ));

        String llmHealth = aiClient.generateCompletion(healthPrompt);
        dto.setRepositoryHealthSummary(llmHealth != null ? llmHealth :
                "EXCELLENT (" + stats.getProjectComplexityScore() + "%). Active automated workflow, modular codebases, and strong documentation.");

        String llmPrediction = aiClient.generateCompletion(predictionPrompt);
        dto.setProjectCompletionPrediction(llmPrediction != null ? llmPrediction :
                "On track for major architecture milestone. Estimated completion velocity: +" + stats.getGrowthVelocityPercent() + "% YoY.");

        return dto;
    }

    @Override
    public AiReplayResponseDto generateReplayScript(AnalyticsOverviewDto analyticsOverview) {
        List<Map<String, String>> replayEvents = new ArrayList<>();
        
        if (analyticsOverview.getTimeline() != null && analyticsOverview.getTimeline().getEvents() != null) {
            for (TimelineEventDto event : analyticsOverview.getTimeline().getEvents()) {
                Map<String, String> card = new HashMap<>();
                card.put("date", event.getDate());
                card.put("text", event.getTitle());
                replayEvents.add(card);
            }
        }

        if (replayEvents.isEmpty()) {
            replayEvents.add(Map.of("date", "January 2021", "text", "Created first repository on GitHub."));
            replayEvents.add(Map.of("date", "June 2021", "text", "Pushed 50th commit and mastered Git workflows."));
            replayEvents.add(Map.of("date", "March 2023", "text", "Architected first full-stack application."));
            replayEvents.add(Map.of("date", "August 2025", "text", "Contributed to open source systems."));
            replayEvents.add(Map.of("date", "Today", "text", "You're still building. The story continues."));
        }

        return new AiReplayResponseDto(analyticsOverview.getUsername(), replayEvents);
    }

    @Override
    public AiWrappedResponseDto generateWrappedSummary(AnalyticsOverviewDto analyticsOverview) {
        DeveloperStatsDto stats = analyticsOverview.getStats();
        LanguageDistributionDto lang = analyticsOverview.getLanguageDistribution();

        AiWrappedResponseDto dto = new AiWrappedResponseDto();
        dto.setUsername(analyticsOverview.getUsername());
        dto.setGrowthSummary("Accelerated commit velocity with +" + stats.getGrowthVelocityPercent() + "% growth year-over-year.");
        dto.setRepositorySummary("Maintained " + stats.getRepositoryActivityScore() + "/100 activity score across " + lang.getFavoriteLanguage() + " codebases.");
        dto.setWrappedNarrative("You are a prolific " + lang.getFavoriteLanguage() + " engineer building high-impact software. The time machine records your journey.");
        return dto;
    }
}
