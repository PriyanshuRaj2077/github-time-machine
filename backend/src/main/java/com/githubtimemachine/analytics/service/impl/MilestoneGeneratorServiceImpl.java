package com.githubtimemachine.analytics.service.impl;

import com.githubtimemachine.analytics.dto.TimelineEventDto;
import com.githubtimemachine.analytics.service.MilestoneGeneratorService;
import com.githubtimemachine.github.dto.GitHubCommitEventDto;
import com.githubtimemachine.github.service.GitHubService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MilestoneGeneratorServiceImpl implements MilestoneGeneratorService {

    private final GitHubService gitHubService;

    public MilestoneGeneratorServiceImpl(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @Override
    public List<TimelineEventDto> generateMilestones(String username) {
        List<GitHubCommitEventDto> commits = gitHubService.fetchCommitHistory(username);
        List<TimelineEventDto> milestones = new ArrayList<>();

        for (GitHubCommitEventDto c : commits) {
            milestones.add(new TimelineEventDto(
                    c.getDate(),
                    c.getText(),
                    "Milestone generated from commit frequency (" + c.getCommitCount() + " commits).",
                    "MILESTONE"
            ));
        }

        return milestones;
    }
}
