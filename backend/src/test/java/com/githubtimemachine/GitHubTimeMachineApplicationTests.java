package com.githubtimemachine;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class GitHubTimeMachineApplicationTests {

    @Test
    void contextLoads() {
        // Verifies Spring context, Security filter chain, and JPA entities load cleanly
    }
}
