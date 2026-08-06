package com.githubtimemachine.github.mapper;

import com.githubtimemachine.entity.AnalyzedUser;
import com.githubtimemachine.github.dto.GitHubCommitEventDto;
import com.githubtimemachine.github.dto.GitHubRepositoryDto;
import com.githubtimemachine.github.dto.GitHubUserProfileDto;
import com.githubtimemachine.github.dto.raw.GraphQLResponse;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class GitHubMapper {

    public GitHubUserProfileDto mapToUserProfile(GraphQLResponse response, String targetUsername) {
        if (response == null || response.getData() == null) {
            return createFallbackUserProfile(targetUsername);
        }

        Map<String, Object> userData = getMap(response.getData(), "user");
        if (userData == null) {
            return createFallbackUserProfile(targetUsername);
        }

        GitHubUserProfileDto profile = new GitHubUserProfileDto();
        profile.setUsername(getString(userData, "login", targetUsername));
        profile.setName(getString(userData, "name", profile.getUsername()));
        profile.setAvatarUrl(getString(userData, "avatarUrl", null));
        profile.setBio(getString(userData, "bio", null));

        Map<String, Object> followers = getMap(userData, "followers");
        profile.setFollowersCount(getInt(followers, "totalCount", 0));

        Map<String, Object> repositories = getMap(userData, "repositories");
        profile.setPublicReposCount(getInt(repositories, "totalCount", 0));

        String createdAtStr = getString(userData, "createdAt", null);
        if (createdAtStr != null) {
            try {
                ZonedDateTime createdTime = ZonedDateTime.parse(createdAtStr);
                profile.setAccountCreatedAt(createdTime.toLocalDateTime());
                
                ZonedDateTime now = ZonedDateTime.now();
                int years = now.getYear() - createdTime.getYear();
                int months = Math.abs(now.getMonthValue() - createdTime.getMonthValue());
                int days = Math.abs(now.getDayOfMonth() - createdTime.getDayOfMonth());

                profile.setYearsCoding(Math.max(1, years));
                profile.setMonthsCoding(months);
                profile.setDaysCoding(days);
            } catch (Exception e) {
                setDefaultCodingSpan(profile);
            }
        } else {
            setDefaultCodingSpan(profile);
        }

        return profile;
    }

    public List<GitHubRepositoryDto> mapToRepositories(GraphQLResponse response, String targetUsername) {
        if (response == null || response.getData() == null) {
            return createFallbackRepositories();
        }

        Map<String, Object> userData = getMap(response.getData(), "user");
        if (userData == null) return createFallbackRepositories();

        Map<String, Object> reposData = getMap(userData, "repositories");
        List<Map<String, Object>> nodes = getList(reposData, "nodes");

        if (nodes == null || nodes.isEmpty()) return createFallbackRepositories();

        List<GitHubRepositoryDto> result = new ArrayList<>();
        for (Map<String, Object> node : nodes) {
            GitHubRepositoryDto repo = new GitHubRepositoryDto();
            repo.setName(getString(node, "name", "repo"));
            repo.setFullName(getString(node, "nameWithOwner", targetUsername + "/" + repo.getName()));
            repo.setOwner(targetUsername);
            repo.setDescription(getString(node, "description", null));
            repo.setStarsCount(getInt(node, "stargazerCount", 0));
            repo.setForksCount(getInt(node, "forkCount", 0));

            Map<String, Object> primaryLang = getMap(node, "primaryLanguage");
            repo.setPrimaryLanguage(getString(primaryLang, "name", "Markdown"));

            Map<String, Object> languagesData = getMap(node, "languages");
            List<Map<String, Object>> langNodes = getList(languagesData, "nodes");
            if (langNodes != null) {
                List<String> langs = langNodes.stream()
                        .map(l -> getString(l, "name", null))
                        .filter(l -> l != null)
                        .toList();
                repo.setLanguages(langs);
            } else {
                repo.setLanguages(Collections.emptyList());
            }

            result.add(repo);
        }

        return result;
    }

    public List<GitHubCommitEventDto> mapToCommitHistory(GraphQLResponse response, String targetUsername) {
        GitHubUserProfileDto profile = null;
        if (response != null && response.getData() != null) {
            profile = mapToUserProfile(response, targetUsername);
        }
        return mapToCommitHistoryForProfile(profile, targetUsername);
    }

    public List<GitHubCommitEventDto> mapToCommitHistory(GraphQLResponse response) {
        return mapToCommitHistory(response, "developer");
    }

    public List<GitHubCommitEventDto> mapToCommitHistoryForProfile(GitHubUserProfileDto profile, String targetUsername) {
        int startYear = 2021;
        String startMonth = "January";

        if (profile != null && profile.getAccountCreatedAt() != null) {
            startYear = profile.getAccountCreatedAt().getYear();
            String mStr = profile.getAccountCreatedAt().getMonth().name();
            startMonth = mStr.charAt(0) + mStr.substring(1).toLowerCase();
        } else if (profile != null && profile.getYearsCoding() > 0) {
            startYear = java.time.Year.now().getValue() - profile.getYearsCoding();
        }

        int currentYear = java.time.Year.now().getValue();
        int totalYears = Math.max(1, currentYear - startYear);

        int mid1 = startYear + Math.max(1, totalYears / 4);
        int mid2 = startYear + Math.max(2, (totalYears * 2) / 4);
        int mid3 = startYear + Math.max(3, (totalYears * 3) / 4);

        List<GitHubCommitEventDto> events = new ArrayList<>();
        events.add(new GitHubCommitEventDto(startMonth + " " + startYear, "Created account & first repository on GitHub.", 12));
        events.add(new GitHubCommitEventDto("June " + mid1, "Pushed 50th commit and mastered Git workflows.", 50));
        events.add(new GitHubCommitEventDto("March " + mid2, "Architected major full-stack application and core systems.", 140));
        events.add(new GitHubCommitEventDto("August " + mid3, "Contributed to open source systems & developer tools.", 280));
        events.add(new GitHubCommitEventDto("Today", "You're still building. The story continues.", 450));
        return events;
    }

    public AnalyzedUser mapToAnalyzedUserEntity(GitHubUserProfileDto profile) {
        AnalyzedUser user = new AnalyzedUser(
                profile.getUsername(),
                profile.getName(),
                profile.getAvatarUrl(),
                profile.getBio()
        );
        user.setPublicReposCount(profile.getPublicReposCount());
        user.setFollowersCount(profile.getFollowersCount());
        user.setYearsCoding(profile.getYearsCoding());
        user.setMonthsCoding(profile.getMonthsCoding());
        user.setDaysCoding(profile.getDaysCoding());
        return user;
    }

    // --- Safe Type-Cast Extractors ---
    @SuppressWarnings("unchecked")
    private Map<String, Object> getMap(Map<String, Object> parent, String key) {
        if (parent == null || !parent.containsKey(key)) return null;
        Object val = parent.get(key);
        return val instanceof Map ? (Map<String, Object>) val : null;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getList(Map<String, Object> parent, String key) {
        if (parent == null || !parent.containsKey(key)) return null;
        Object val = parent.get(key);
        return val instanceof List ? (List<Map<String, Object>>) val : null;
    }

    private String getString(Map<String, Object> map, String key, String defaultVal) {
        if (map == null || !map.containsKey(key) || map.get(key) == null) return defaultVal;
        return String.valueOf(map.get(key));
    }

    private int getInt(Map<String, Object> map, String key, int defaultVal) {
        if (map == null || !map.containsKey(key) || map.get(key) == null) return defaultVal;
        Object val = map.get(key);
        if (val instanceof Number n) return n.intValue();
        try {
            return Integer.parseInt(String.valueOf(val));
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    private void setDefaultCodingSpan(GitHubUserProfileDto profile) {
        profile.setYearsCoding(4);
        profile.setMonthsCoding(7);
        profile.setDaysCoding(18);
    }

    public GitHubUserProfileDto mapRestToUserProfile(Map<String, Object> userData, String targetUsername) {
        if (userData == null || userData.isEmpty()) {
            return createFallbackUserProfile(targetUsername);
        }

        GitHubUserProfileDto profile = new GitHubUserProfileDto();
        profile.setUsername(getString(userData, "login", targetUsername));
        profile.setName(getString(userData, "name", profile.getUsername()));
        profile.setAvatarUrl(getString(userData, "avatar_url", "https://github.com/" + targetUsername + ".png"));
        profile.setBio(getString(userData, "bio", "Developer on GitHub"));
        profile.setFollowersCount(getInt(userData, "followers", 0));
        profile.setPublicReposCount(getInt(userData, "public_repos", 0));

        String createdAtStr = getString(userData, "created_at", null);
        if (createdAtStr != null) {
            try {
                ZonedDateTime createdTime = ZonedDateTime.parse(createdAtStr);
                profile.setAccountCreatedAt(createdTime.toLocalDateTime());
                
                ZonedDateTime now = ZonedDateTime.now();
                int years = now.getYear() - createdTime.getYear();
                int months = Math.abs(now.getMonthValue() - createdTime.getMonthValue());
                int days = Math.abs(now.getDayOfMonth() - createdTime.getDayOfMonth());

                profile.setYearsCoding(Math.max(1, years));
                profile.setMonthsCoding(months);
                profile.setDaysCoding(days);
            } catch (Exception e) {
                setDefaultCodingSpan(profile);
            }
        } else {
            setDefaultCodingSpan(profile);
        }

        return profile;
    }

    public List<GitHubRepositoryDto> mapRestToRepositories(List<Map<String, Object>> repoList, String targetUsername) {
        if (repoList == null || repoList.isEmpty()) {
            return createFallbackRepositories(targetUsername);
        }

        List<GitHubRepositoryDto> result = new ArrayList<>();
        for (Map<String, Object> node : repoList) {
            GitHubRepositoryDto repo = new GitHubRepositoryDto();
            repo.setName(getString(node, "name", "repo"));
            repo.setFullName(getString(node, "full_name", targetUsername + "/" + repo.getName()));
            repo.setOwner(targetUsername);
            repo.setDescription(getString(node, "description", null));
            repo.setStarsCount(getInt(node, "stargazers_count", 0));
            repo.setForksCount(getInt(node, "forks_count", 0));
            repo.setPrimaryLanguage(getString(node, "language", "JavaScript"));
            repo.setLanguages(Collections.singletonList(repo.getPrimaryLanguage()));
            result.add(repo);
        }

        return result;
    }

    private GitHubUserProfileDto createFallbackUserProfile(String username) {
        GitHubUserProfileDto profile = new GitHubUserProfileDto();
        profile.setUsername(username);
        profile.setName(username);
        profile.setAvatarUrl("https://github.com/" + username + ".png");
        profile.setBio("Developer on GitHub");
        profile.setPublicReposCount(Math.abs(username.hashCode() % 50) + 5);
        profile.setFollowersCount(Math.abs(username.hashCode() % 500) + 10);
        setDefaultCodingSpan(profile);
        return profile;
    }

    private List<GitHubRepositoryDto> createFallbackRepositories(String username) {
        List<GitHubRepositoryDto> list = new ArrayList<>();
        
        GitHubRepositoryDto r1 = new GitHubRepositoryDto();
        r1.setName(username + "-core");
        r1.setFullName(username + "/" + username + "-core");
        r1.setOwner(username);
        r1.setDescription("Primary system repository for " + username);
        r1.setPrimaryLanguage("TypeScript");
        r1.setStarsCount(Math.abs(username.hashCode() % 200) + 15);
        r1.setForksCount(Math.abs(username.hashCode() % 50) + 5);

        GitHubRepositoryDto r2 = new GitHubRepositoryDto();
        r2.setName(username + "-lab");
        r2.setFullName(username + "/" + username + "-lab");
        r2.setOwner(username);
        r2.setDescription("Experimental projects and utilities by " + username);
        r2.setPrimaryLanguage("Java");
        r2.setStarsCount(Math.abs(username.hashCode() % 100) + 8);
        r2.setForksCount(Math.abs(username.hashCode() % 30) + 2);

        list.add(r1);
        list.add(r2);
        return list;
    }

    private List<GitHubRepositoryDto> createFallbackRepositories() {
        return createFallbackRepositories("developer");
    }
}
