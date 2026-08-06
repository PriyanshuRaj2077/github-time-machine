package com.githubtimemachine.analytics.service;

import com.githubtimemachine.analytics.dto.DeveloperStatsDto;

public interface DeveloperStatisticsService {
    DeveloperStatsDto computeDeveloperStats(String username);
}
