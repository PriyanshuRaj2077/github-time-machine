package com.githubtimemachine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "analysis_history", indexes = {
        @Index(name = "idx_history_user_id", columnList = "user_id"),
        @Index(name = "idx_history_target", columnList = "target")
})
public class AnalysisHistory extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "target", nullable = false)
    private String target;

    @Column(name = "analysis_type", nullable = false)
    private String analysisType;

    public AnalysisHistory() {
    }

    public AnalysisHistory(UUID userId, String target, String analysisType) {
        this.userId = userId;
        this.target = target;
        this.analysisType = analysisType;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }
}
