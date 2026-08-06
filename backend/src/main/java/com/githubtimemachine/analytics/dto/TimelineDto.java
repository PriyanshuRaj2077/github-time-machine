package com.githubtimemachine.analytics.dto;

import java.util.List;

public class TimelineDto {

    private String username;
    private List<TimelineEventDto> events;

    public TimelineDto() {
    }

    public TimelineDto(String username, List<TimelineEventDto> events) {
        this.username = username;
        this.events = events;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<TimelineEventDto> getEvents() {
        return events;
    }

    public void setEvents(List<TimelineEventDto> events) {
        this.events = events;
    }
}
