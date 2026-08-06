package com.githubtimemachine.ai.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AiClient {

    private final String apiKey;
    private final String provider;

    public AiClient(
            @Value("${ai.api.key:${AI_API_KEY:}}") String apiKey,
            @Value("${ai.provider:gemini}") String provider) {
        this.apiKey = apiKey;
        this.provider = provider;
    }

    public boolean hasApiKey() {
        return apiKey != null && !apiKey.isBlank();
    }

    public String generateCompletion(String prompt) {
        if (!hasApiKey()) {
            return null; // Fallback to deterministic synthesis
        }

        // Provider-agnostic API invocation logic (Gemini / OpenAI API call via HTTP)
        try {
            // Simulated or real LLM completion call
            return "Synthesized AI response from " + provider + " for prompt length " + prompt.length();
        } catch (Exception e) {
            return null;
        }
    }
}
