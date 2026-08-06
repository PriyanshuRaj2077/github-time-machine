package com.githubtimemachine.github.query;

import org.springframework.stereotype.Component;

@Component
public class GraphQLQueryBuilder {

    /**
     * GraphQL query to fetch user profile, contribution calendar, repositories, languages, stars, forks, and commit history.
     */
    public String buildUserComprehensiveQuery(String username) {
        return """
            query GetUserOverview($username: String!) {
              user(login: $username) {
                login
                name
                avatarUrl
                bio
                createdAt
                followers {
                  totalCount
                }
                repositories(first: 30, orderBy: {field: STARGAZERS, direction: DESC}, ownerAffiliations: OWNER) {
                  totalCount
                  nodes {
                    name
                    nameWithOwner
                    description
                    stargazerCount
                    forkCount
                    createdAt
                    updatedAt
                    primaryLanguage {
                      name
                    }
                    languages(first: 5) {
                      nodes {
                        name
                      }
                    }
                  }
                }
                contributionsCollection {
                  startedAt
                  endedAt
                  totalCommitContributions
                  contributionCalendar {
                    totalContributions
                    weeks {
                      contributionDays {
                        date
                        contributionCount
                      }
                    }
                  }
                }
              }
            }
            """;
    }

    /**
     * Query specifically for a repository metadata search
     */
    public String buildRepositoryMetadataQuery(String owner, String repoName) {
        return """
            query GetRepositoryDetails($owner: String!, $name: String!) {
              repository(owner: $owner, name: $name) {
                name
                nameWithOwner
                description
                stargazerCount
                forkCount
                createdAt
                updatedAt
                primaryLanguage {
                  name
                }
                owner {
                  login
                  avatarUrl
                }
              }
            }
            """;
    }
}
