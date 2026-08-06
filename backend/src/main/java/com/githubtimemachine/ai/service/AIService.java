package com.githubtimemachine.ai.service;

import com.githubtimemachine.ai.dto.AiInsightsResponseDto;
import com.githubtimemachine.ai.dto.AiReplayResponseDto;
import com.githubtimemachine.ai.dto.AiWrappedResponseDto;
import com.githubtimemachine.analytics.dto.AnalyticsOverviewDto;

public interface AIService {

    AiInsightsResponseDto generateInsights(AnalyticsOverviewDto analyticsOverview);

    AiReplayResponseDto generateReplayScript(AnalyticsOverviewDto analyticsOverview);

    AiWrappedResponseDto generateWrappedSummary(AnalyticsOverviewDto analyticsOverview);
}
