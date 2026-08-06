package com.githubtimemachine.analytics.service;

import com.githubtimemachine.analytics.dto.AnalyticsOverviewDto;

public interface AnalyticsEngineFacadeService {
    AnalyticsOverviewDto getAnalyticsOverview(String username);
}
