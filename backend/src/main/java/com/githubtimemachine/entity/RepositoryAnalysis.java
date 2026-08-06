package com.githubtimemachine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "repository_analysis", indexes = {
        @Index(name = "idx_repo_analysis_owner_name", columnList = "owner, repository_name")
})
public class RepositoryAnalysis extends BaseEntity {

    @Column(name = "repository_name", nullable = false)
    private String repositoryName;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Lob
    @Column(name = "analysis", columnDefinition = "TEXT")
    private String analysis;

    public RepositoryAnalysis() {
    }

    public RepositoryAnalysis(String repositoryName, String owner, String analysis) {
        this.repositoryName = repositoryName;
        this.owner = owner;
        this.analysis = analysis;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
