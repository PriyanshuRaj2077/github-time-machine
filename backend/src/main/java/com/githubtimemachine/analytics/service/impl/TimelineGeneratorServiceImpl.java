package com.githubtimemachine.analytics.service.impl;

import com.githubtimemachine.analytics.dto.TimelineDto;
import com.githubtimemachine.analytics.dto.TimelineEventDto;
import com.githubtimemachine.analytics.service.MilestoneGeneratorService;
import com.githubtimemachine.analytics.service.TimelineGeneratorService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimelineGeneratorServiceImpl implements TimelineGeneratorService {

    private final MilestoneGeneratorService milestoneGeneratorService;

    public TimelineGeneratorServiceImpl(MilestoneGeneratorService milestoneGeneratorService) {
        this.milestoneGeneratorService = milestoneGeneratorService;
    }

    @Override
    @Cacheable(value = "timelines", key = "#username")
    public TimelineDto generateTimeline(String username) {
        List<TimelineEventDto> events = milestoneGeneratorService.generateMilestones(username);
        return new TimelineDto(username, events);
    }
}
