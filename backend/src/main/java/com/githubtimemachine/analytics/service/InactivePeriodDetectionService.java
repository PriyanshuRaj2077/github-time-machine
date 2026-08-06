package com.githubtimemachine.analytics.service;

import java.util.List;

public interface InactivePeriodDetectionService {
    List<String> detectInactivePeriods(String username);
}
