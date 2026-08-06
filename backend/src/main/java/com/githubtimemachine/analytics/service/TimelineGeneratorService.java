package com.githubtimemachine.analytics.service;

import com.githubtimemachine.analytics.dto.TimelineDto;

public interface TimelineGeneratorService {
    TimelineDto generateTimeline(String username);
}
