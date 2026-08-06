package com.githubtimemachine.controller;

import com.githubtimemachine.entity.Role;
import com.githubtimemachine.entity.User;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@Transactional
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    @DisplayName("GET /api/admin/dashboard with ROLE_ADMIN should return 200 OK with admin metrics")
    void getDashboardStats_AdminRole_ShouldSucceed() throws Exception {
        User adminUser = new User(11111111L, "admin-user", "Admin User", "https://avatar.com/a", "Bio", "admin@test.com", Role.ROLE_ADMIN);
        User savedAdmin = userRepository.save(adminUser);

        UserPrincipal principal = UserPrincipal.create(savedAdmin);
        String jwtToken = jwtUtils.generateToken(principal);

        mockMvc.perform(get("/api/admin/dashboard")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.totalUsers", notNullValue()))
                .andExpect(jsonPath("$.data.databaseStatus", containsString("Healthy")));
    }

    @Test
    @DisplayName("GET /api/admin/dashboard with ROLE_USER should be rejected with 403 Forbidden")
    void getDashboardStats_UserRole_ShouldBeForbidden() throws Exception {
        User normalUser = new User(22222222L, "normal-user", "Normal User", "https://avatar.com/n", "Bio", "user@test.com", Role.ROLE_USER);
        User savedUser = userRepository.save(normalUser);

        UserPrincipal principal = UserPrincipal.create(savedUser);
        String jwtToken = jwtUtils.generateToken(principal);

        mockMvc.perform(get("/api/admin/dashboard")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /api/admin/dashboard unauthenticated should be rejected with 401/403")
    void getDashboardStats_Unauthenticated_ShouldFail() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().is4xxClientError());
    }

    private static org.hamcrest.Matcher<String> containsString(String sub) {
        return org.hamcrest.Matchers.containsStringIgnoringCase(sub);
    }
}
