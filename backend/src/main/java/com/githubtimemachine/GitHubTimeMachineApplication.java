package com.githubtimemachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
})
@EnableJpaAuditing
@EnableCaching
public class GitHubTimeMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitHubTimeMachineApplication.class, args);
    }
}
