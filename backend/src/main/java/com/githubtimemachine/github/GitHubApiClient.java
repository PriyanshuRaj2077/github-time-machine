package com.githubtimemachine.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitHubApiClient {

    private final String baseUrl;
    private final String graphqlUrl;
    private final String apiToken;

    public GitHubApiClient(
            @Value("${github.api.base-url}") String baseUrl,
            @Value("${github.graphql.url}") String graphqlUrl,
            @Value("${github.api.token}") String apiToken) {
        this.baseUrl = baseUrl;
        this.graphqlUrl = graphqlUrl;
        this.apiToken = apiToken;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getGraphqlUrl() {
        return graphqlUrl;
    }

    public boolean hasToken() {
        return apiToken != null && !apiToken.isBlank();
    }
}
