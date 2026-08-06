package com.githubtimemachine.analytics;

import org.springframework.stereotype.Component;

@Component
public class AnalyticsProcessor {

    public AnalyticsProcessor() {
        // Constructor injection ready
    }

    public int computeArchitectureScore(int repoCount, int followerCount) {
        int base = 75;
        int bonus = Math.min(25, (repoCount * 2) + (followerCount / 10));
        return Math.min(100, base + bonus);
    }
}
