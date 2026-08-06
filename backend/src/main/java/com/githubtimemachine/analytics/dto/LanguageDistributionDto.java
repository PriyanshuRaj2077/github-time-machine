package com.githubtimemachine.analytics.dto;

import java.util.List;
import java.util.Map;

public class LanguageDistributionDto {

    private String favoriteLanguage;
    private Map<String, Double> languagePercentages;
    private List<String> detectedSkills;

    public LanguageDistributionDto() {
    }

    public LanguageDistributionDto(String favoriteLanguage, Map<String, Double> languagePercentages, List<String> detectedSkills) {
        this.favoriteLanguage = favoriteLanguage;
        this.languagePercentages = languagePercentages;
        this.detectedSkills = detectedSkills;
    }

    public String getFavoriteLanguage() {
        return favoriteLanguage;
    }

    public void setFavoriteLanguage(String favoriteLanguage) {
        this.favoriteLanguage = favoriteLanguage;
    }

    public Map<String, Double> getLanguagePercentages() {
        return languagePercentages;
    }

    public void setLanguagePercentages(Map<String, Double> languagePercentages) {
        this.languagePercentages = languagePercentages;
    }

    public List<String> getDetectedSkills() {
        return detectedSkills;
    }

    public void setDetectedSkills(List<String> detectedSkills) {
        this.detectedSkills = detectedSkills;
    }
}
