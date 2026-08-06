package com.githubtimemachine.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.githubtimemachine.repository")
public class JpaConfig {
    // JPA configuration bean customizations can be added here
}
