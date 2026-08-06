package com.githubtimemachine.github.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubtimemachine.github.dto.raw.GraphQLRequest;
import com.githubtimemachine.github.dto.raw.GraphQLResponse;
import com.githubtimemachine.github.exception.GitHubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Component
public class GitHubClient {

    private static final Logger log = LoggerFactory.getLogger(GitHubClient.class);
    private static final String GITHUB_GRAPHQL_URL = "https://api.github.com/graphql";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String githubToken;

    public GitHubClient(
            ObjectMapper objectMapper,
            @Value("${github.api.token:}") String githubToken) {
        this.objectMapper = objectMapper;
        this.githubToken = githubToken;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public GraphQLResponse executeGraphQL(String query, Map<String, Object> variables) {
        if (githubToken == null || githubToken.isBlank()) {
            log.warn("[GitHubClient] GITHUB_API_TOKEN is not set. Operating in fallback mode.");
            return null;
        }

        try {
            GraphQLRequest payload = new GraphQLRequest(query, variables);
            String jsonPayload = objectMapper.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GITHUB_GRAPHQL_URL))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + githubToken)
                    .header("Accept-Encoding", "gzip, deflate")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Exponential backoff retry loop (Max 3 attempts)
            int maxRetries = 3;
            long backoffMs = 500;

            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

                    if (response.statusCode() == 200) {
                        String encoding = response.headers().firstValue("Content-Encoding").orElse("");
                        InputStream responseBody = "gzip".equalsIgnoreCase(encoding) 
                                ? new GZIPInputStream(response.body()) 
                                : response.body();

                        return objectMapper.readValue(responseBody, GraphQLResponse.class);
                    }

                    if (response.statusCode() == 429) {
                        String retryAfter = response.headers().firstValue("Retry-After").orElse("5");
                        log.warn("[GitHubClient] GitHub API rate limit hit (HTTP 429). Retry-After: {}s", retryAfter);
                        throw new GitHubException("GitHub API rate limit exceeded.", 429);
                    }

                    log.warn("[GitHubClient] GraphQL API attempt {} returned HTTP {}", attempt, response.statusCode());

                } catch (Exception e) {
                    if (attempt == maxRetries) throw e;
                }

                Thread.sleep(backoffMs);
                backoffMs *= 2;
            }

            return null;

        } catch (GitHubException e) {
            throw e;
        } catch (Exception e) {
            log.error("[GitHubClient] Error executing GraphQL query against GitHub API", e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> executeRestGetMap(String path) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com" + path))
                    .timeout(Duration.ofSeconds(10))
                    .header("User-Agent", "GitHub-Time-Machine")
                    .header("Accept", "application/json");
            if (githubToken != null && !githubToken.isBlank()) {
                builder.header("Authorization", "Bearer " + githubToken);
            }
            HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), Map.class);
            }
        } catch (Exception e) {
            log.warn("[GitHubClient] REST GET {} failed: {}", path, e.getMessage());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> executeRestGetList(String path) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com" + path))
                    .timeout(Duration.ofSeconds(10))
                    .header("User-Agent", "GitHub-Time-Machine")
                    .header("Accept", "application/json");
            if (githubToken != null && !githubToken.isBlank()) {
                builder.header("Authorization", "Bearer " + githubToken);
            }
            HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), List.class);
            }
        } catch (Exception e) {
            log.warn("[GitHubClient] REST GET {} failed: {}", path, e.getMessage());
        }
        return null;
    }
}
