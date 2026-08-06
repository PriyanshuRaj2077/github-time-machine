package com.githubtimemachine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubtimemachine.dto.auth.OAuthCodeRequestDto;
import com.githubtimemachine.entity.Role;
import com.githubtimemachine.entity.User;
import com.githubtimemachine.repository.UserRepository;
import com.githubtimemachine.security.UserPrincipal;
import com.githubtimemachine.security.jwt.JwtUtils;
import com.githubtimemachine.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("GET /api/auth/github/url should return valid GitHub authorization URL")
    void getGitHubAuthUrl_ShouldReturnUrl() throws Exception {
        mockMvc.perform(get("/api/auth/github/url"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.url", containsString("https://github.com/login/oauth/authorize")));
    }

    @Test
    @DisplayName("POST /api/auth/github/callback should process authorization code and return JWT + User DTO")
    void handleGitHubCallback_ShouldAuthenticateAndReturnJwt() throws Exception {
        OAuthCodeRequestDto request = new OAuthCodeRequestDto("demo-test-code");

        mockMvc.perform(post("/api/auth/github/callback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.token", notNullValue()))
                .andExpect(jsonPath("$.data.user.username", is("github-developer")))
                .andExpect(jsonPath("$.data.user.role", is("ROLE_USER")));
    }

    @Test
    @DisplayName("GET /api/auth/me should return authenticated user profile when valid Bearer token provided")
    void getCurrentUser_WithValidToken_ShouldReturnUser() throws Exception {
        // Create user in DB
        User user = new User(88888888L, "test-user", "Test User", "https://avatar.com/u", "Bio", "test@test.com", Role.ROLE_USER);
        User savedUser = userRepository.save(user);

        UserPrincipal principal = UserPrincipal.create(savedUser);
        String jwtToken = jwtUtils.generateToken(principal);

        mockMvc.perform(get("/api/auth/me")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.username", is("test-user")))
                .andExpect(jsonPath("$.data.email", is("test@test.com")));
    }

    @Test
    @DisplayName("GET /api/auth/me should return 401/403 when unauthenticated")
    void getCurrentUser_Unauthenticated_ShouldFail() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().is4xxClientError());
    }
}
