package com.githubtimemachine.analytics.service;

import com.githubtimemachine.analytics.dto.StreakDto;

public interface CommitStreakAnalyzerService {
    StreakDto analyzeStreak(String username);
}
