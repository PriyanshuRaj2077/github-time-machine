package com.githubtimemachine.github.service;

import com.githubtimemachine.github.dto.GitHubCommitEventDto;
import com.githubtimemachine.github.dto.GitHubRepositoryDto;
import com.githubtimemachine.github.dto.GitHubUserProfileDto;

import java.util.List;
import java.util.Map;

public interface GitHubService {

    GitHubUserProfileDto fetchUserProfile(String username);

    List<GitHubRepositoryDto> fetchRepositories(String username);

    List<String> fetchLanguages(String username);

    int fetchTotalStars(String username);

    int fetchTotalForks(String username);

    List<GitHubCommitEventDto> fetchCommitHistory(String username);

    Map<String, Object> fetchContributionDates(String username);

    GitHubRepositoryDto fetchRepositoryMetadata(String owner, String repoName);
}
