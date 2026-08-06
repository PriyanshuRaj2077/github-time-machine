package com.githubtimemachine.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AnalysisRequestDto {

    @NotBlank(message = "Username or repository URL is required")
    @Size(min = 1, max = 255, message = "Target query length must be between 1 and 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\-\\._\\/:]+$", message = "Target query contains invalid characters")
    private String targetQuery;

    private Boolean forceRefresh = false;

    public AnalysisRequestDto() {
    }

    public AnalysisRequestDto(String targetQuery) {
        this.targetQuery = targetQuery;
    }

    public String getTargetQuery() {
        return targetQuery;
    }

    public void setTargetQuery(String targetQuery) {
        this.targetQuery = targetQuery;
    }

    public Boolean getForceRefresh() {
        return forceRefresh;
    }

    public void setForceRefresh(Boolean forceRefresh) {
        this.forceRefresh = forceRefresh;
    }
}
