package com.githubtimemachine.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class AnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/analyze/{target} should perform public analysis without requiring login")
    void analyzeTarget_PublicMode_ShouldSucceedWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/analyze/torvalds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.username", is("torvalds")));
    }

    @Test
    @DisplayName("GET /api/profile/{username} should return profile metadata publicly")
    void getProfile_PublicMode_ShouldReturnProfile() throws Exception {
        mockMvc.perform(get("/api/profile/torvalds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.username", is("torvalds")));
    }

    @Test
    @DisplayName("GET /api/repositories/{username} should return repository snapshots publicly")
    void getRepositories_PublicMode_ShouldReturnRepos() throws Exception {
        mockMvc.perform(get("/api/repositories/torvalds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/timeline/{username} should return timeline milestones publicly")
    void getTimeline_PublicMode_ShouldReturnMilestones() throws Exception {
        mockMvc.perform(get("/api/timeline/torvalds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("GET /api/replay/{username} should return documentary replay sequence publicly")
    void getReplay_PublicMode_ShouldReturnReplaySlides() throws Exception {
        mockMvc.perform(get("/api/replay/torvalds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(1)));
    }
}
