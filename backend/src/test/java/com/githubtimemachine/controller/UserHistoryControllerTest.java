package com.githubtimemachine.controller;

import com.githubtimemachine.entity.AnalysisHistory;
import com.githubtimemachine.entity.Role;
import com.githubtimemachine.entity.User;
import com.githubtimemachine.repository.AnalysisHistoryRepository;
import com.githubtimemachine.repository.UserRepository;
import com.githubtimemachine.security.UserPrincipal;
import com.githubtimemachine.security.jwt.JwtUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Transactional
class UserHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnalysisHistoryRepository historyRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    @DisplayName("GET /api/user/history should return saved history items for authenticated user")
    void getMyHistory_Authenticated_ShouldReturnUserHistory() throws Exception {
        User user = new User(99999999L, "history-user", "History User", "https://avatar.com/h", "Bio", "history@test.com", Role.ROLE_USER);
        User savedUser = userRepository.save(user);

        historyRepository.save(new AnalysisHistory(savedUser.getId(), "torvalds", "USER_PROFILE"));
        historyRepository.save(new AnalysisHistory(savedUser.getId(), "facebook/react", "REPOSITORY"));

        UserPrincipal principal = UserPrincipal.create(savedUser);
        String jwtToken = jwtUtils.generateToken(principal);

        mockMvc.perform(get("/api/user/history")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].target", is("facebook/react")))
                .andExpect(jsonPath("$.data[1].target", is("torvalds")));
    }

    @Test
    @DisplayName("GET /api/user/history without token should fail with 401/403 Client Error")
    void getMyHistory_Unauthenticated_ShouldFail() throws Exception {
        mockMvc.perform(get("/api/user/history"))
                .andExpect(status().is4xxClientError());
    }
}
