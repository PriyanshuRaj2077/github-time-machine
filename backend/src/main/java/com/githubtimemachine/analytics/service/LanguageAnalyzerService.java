package com.githubtimemachine.analytics.service;

import com.githubtimemachine.analytics.dto.LanguageDistributionDto;

public interface LanguageAnalyzerService {
    LanguageDistributionDto analyzeLanguages(String username);
}
