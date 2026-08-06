package com.githubtimemachine.analytics.service;

import com.githubtimemachine.analytics.dto.TimelineEventDto;

import java.util.List;

public interface MilestoneGeneratorService {
    List<TimelineEventDto> generateMilestones(String username);
}
