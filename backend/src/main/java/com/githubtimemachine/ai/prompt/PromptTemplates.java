package com.githubtimemachine.ai.prompt;

public final class PromptTemplates {

    private PromptTemplates() {
        // Utility class constructor
    }

    public static final String SYSTEM_INSTRUCTION = """
        You are an expert AI Software Architect and Tech Historian for GitHub Time Machine.
        Your task is to analyze developer statistics and produce cinematic, developer-first insights.
        Never invent false data. Use only the provided analytics payload.
        """;

    public static final String DEVELOPER_PERSONALITY = """
        Based on the following developer analytics:
        - Primary Language: %s
        - Active Streak: %d days
        - Growth Velocity: %.1f%%
        - Detected Skills: %s
        
        Generate a 2-sentence developer personality archetype description.
        """;

    public static final String DOCUMENTARY_TIMELINE = """
        Convert the following timeline events into a cinematic 5-slide documentary script:
        - Username: %s
        - Milestones: %s
        """;

    public static final String REPOSITORY_HEALTH = """
        Analyze repository activity score (%d/100) and complexity score (%d/100):
        Provide a concise 1-sentence repository health summary.
        """;

    public static final String PROJECT_COMPLETION_PREDICTION = """
        Based on activity score %d and growth velocity %.1f%%:
        Estimate project completion probability and delivery timeline.
        """;

    public static final String DEVELOPER_DNA = """
        Generate a 3-keyword Developer DNA profile based on favorite language (%s) and skills (%s).
        """;
}
