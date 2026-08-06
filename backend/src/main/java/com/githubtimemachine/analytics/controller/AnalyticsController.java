package com.githubtimemachine.analytics.controller;

import com.githubtimemachine.analytics.dto.AnalyticsOverviewDto;
import com.githubtimemachine.analytics.dto.StreakDto;
import com.githubtimemachine.analytics.dto.TimelineDto;
import com.githubtimemachine.analytics.service.AnalyticsEngineFacadeService;
import com.githubtimemachine.analytics.service.CommitStreakAnalyzerService;
import com.githubtimemachine.analytics.service.TimelineGeneratorService;
import com.githubtimemachine.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsEngineFacadeService analyticsEngineFacadeService;
    private final TimelineGeneratorService timelineGeneratorService;
    private final CommitStreakAnalyzerService commitStreakAnalyzerService;

    // Strict Constructor Injection ONLY (No @Autowired field injection)
    public AnalyticsController(
            AnalyticsEngineFacadeService analyticsEngineFacadeService,
            TimelineGeneratorService timelineGeneratorService,
            CommitStreakAnalyzerService commitStreakAnalyzerService) {
        this.analyticsEngineFacadeService = analyticsEngineFacadeService;
        this.timelineGeneratorService = timelineGeneratorService;
        this.commitStreakAnalyzerService = commitStreakAnalyzerService;
    }

    @GetMapping("/analytics/{username}")
    public ResponseEntity<ApiResponse<AnalyticsOverviewDto>> getAnalyticsOverview(@PathVariable String username) {
        AnalyticsOverviewDto overview = analyticsEngineFacadeService.getAnalyticsOverview(username);
        return ResponseEntity.ok(ApiResponse.success("Analytics computed successfully", overview));
    }

    @GetMapping("/timeline/{username}")
    public ResponseEntity<ApiResponse<TimelineDto>> getTimeline(@PathVariable String username) {
        TimelineDto timeline = timelineGeneratorService.generateTimeline(username);
        return ResponseEntity.ok(ApiResponse.success("Timeline generated successfully", timeline));
    }

    @GetMapping("/streak/{username}")
    public ResponseEntity<ApiResponse<StreakDto>> getStreak(@PathVariable String username) {
        StreakDto streak = commitStreakAnalyzerService.analyzeStreak(username);
        return ResponseEntity.ok(ApiResponse.success("Streak analytics computed successfully", streak));
    }
}
