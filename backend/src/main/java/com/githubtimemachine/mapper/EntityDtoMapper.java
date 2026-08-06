package com.githubtimemachine.mapper;

import com.githubtimemachine.dto.response.AnalyticsSnapshotResponseDto;
import com.githubtimemachine.dto.response.AnalyzedUserResponseDto;
import com.githubtimemachine.dto.response.RepositorySnapshotResponseDto;
import com.githubtimemachine.entity.AnalyticsSnapshot;
import com.githubtimemachine.entity.AnalyzedUser;
import com.githubtimemachine.entity.RepositorySnapshot;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoMapper {

    public AnalyzedUserResponseDto toUserResponseDto(AnalyzedUser user) {
        if (user == null) return null;
        AnalyzedUserResponseDto dto = new AnalyzedUserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setBio(user.getBio());
        dto.setPublicReposCount(user.getPublicReposCount());
        dto.setFollowersCount(user.getFollowersCount());
        dto.setYearsCoding(user.getYearsCoding());
        dto.setMonthsCoding(user.getMonthsCoding());
        dto.setDaysCoding(user.getDaysCoding());
        return dto;
    }

    public RepositorySnapshotResponseDto toRepoResponseDto(RepositorySnapshot repo) {
        if (repo == null) return null;
        RepositorySnapshotResponseDto dto = new RepositorySnapshotResponseDto();
        dto.setId(repo.getId());
        dto.setRepoName(repo.getRepoName());
        dto.setFullName(repo.getFullName());
        dto.setOwner(repo.getOwner());
        dto.setDescription(repo.getDescription());
        dto.setPrimaryLanguage(repo.getPrimaryLanguage());
        dto.setStarsCount(repo.getStarsCount());
        dto.setForksCount(repo.getForksCount());
        dto.setCodebaseAge(repo.getCodebaseAge());
        return dto;
    }

    public AnalyticsSnapshotResponseDto toAnalyticsResponseDto(AnalyticsSnapshot snapshot) {
        if (snapshot == null) return null;
        AnalyticsSnapshotResponseDto dto = new AnalyticsSnapshotResponseDto();
        dto.setId(snapshot.getId());
        dto.setArchitectureScore(snapshot.getArchitectureScore());
        dto.setRepositoryHealth(snapshot.getRepositoryHealth());
        dto.setContributionDifficulty(snapshot.getContributionDifficulty());
        dto.setAiSummary(snapshot.getAiSummary());
        dto.setEvolutionPhases(snapshot.getEvolutionPhases());
        return dto;
    }
}
