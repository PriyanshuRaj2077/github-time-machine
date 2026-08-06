package com.githubtimemachine.ai.dto;

import java.util.List;
import java.util.Map;

public class AiReplayResponseDto {

    private String username;
    private List<Map<String, String>> replayEvents;

    public AiReplayResponseDto() {
    }

    public AiReplayResponseDto(String username, List<Map<String, String>> replayEvents) {
        this.username = username;
        this.replayEvents = replayEvents;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Map<String, String>> getReplayEvents() {
        return replayEvents;
    }

    public void setReplayEvents(List<Map<String, String>> replayEvents) {
        this.replayEvents = replayEvents;
    }
}
