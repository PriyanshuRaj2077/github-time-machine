package com.githubtimemachine.analytics.service.impl;

import com.githubtimemachine.analytics.service.InactivePeriodDetectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InactivePeriodDetectionServiceImpl implements InactivePeriodDetectionService {

    public InactivePeriodDetectionServiceImpl() {
    }

    @Override
    public List<String> detectInactivePeriods(String username) {
        return List.of(
                "2022-06-12 to 2022-08-15 (64 Days Hiatus)",
                "2023-11-01 to 2023-11-28 (27 Days Rest Period)"
        );
    }
}
