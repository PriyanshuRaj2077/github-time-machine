package com.githubtimemachine.ai.controller;

import com.githubtimemachine.ai.dto.AiInsightsResponseDto;
import com.githubtimemachine.ai.dto.AiReplayResponseDto;
import com.githubtimemachine.ai.dto.AiWrappedResponseDto;
import com.githubtimemachine.ai.service.AIService;
import com.githubtimemachine.analytics.dto.AnalyticsOverviewDto;
import com.githubtimemachine.analytics.service.AnalyticsEngineFacadeService;
import com.githubtimemachine.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AIService aiService;
    private final AnalyticsEngineFacadeService analyticsEngineFacadeService;

    // Strict Constructor Injection ONLY (No @Autowired field injection)
    public AiController(AIService aiService, AnalyticsEngineFacadeService analyticsEngineFacadeService) {
        this.aiService = aiService;
        this.analyticsEngineFacadeService = analyticsEngineFacadeService;
    }

    @GetMapping("/insights/{username}")
    public ResponseEntity<ApiResponse<AiInsightsResponseDto>> getInsights(@PathVariable String username) {
        AnalyticsOverviewDto analytics = analyticsEngineFacadeService.getAnalyticsOverview(username);
        AiInsightsResponseDto insights = aiService.generateInsights(analytics);
        return ResponseEntity.ok(ApiResponse.success("AI Insights generated successfully", insights));
    }

    @GetMapping("/replay/{username}")
    public ResponseEntity<ApiResponse<AiReplayResponseDto>> getReplayScript(@PathVariable String username) {
        AnalyticsOverviewDto analytics = analyticsEngineFacadeService.getAnalyticsOverview(username);
        AiReplayResponseDto replay = aiService.generateReplayScript(analytics);
        return ResponseEntity.ok(ApiResponse.success("AI Replay script generated successfully", replay));
    }

    @GetMapping("/wrapped/{username}")
    public ResponseEntity<ApiResponse<AiWrappedResponseDto>> getWrappedSummary(@PathVariable String username) {
        AnalyticsOverviewDto analytics = analyticsEngineFacadeService.getAnalyticsOverview(username);
        AiWrappedResponseDto wrapped = aiService.generateWrappedSummary(analytics);
        return ResponseEntity.ok(ApiResponse.success("AI Wrapped summary generated successfully", wrapped));
    }
}
