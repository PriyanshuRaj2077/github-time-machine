package com.githubtimemachine.service.impl;

import com.githubtimemachine.dto.auth.AuthResponseDto;
import com.githubtimemachine.entity.Role;
import com.githubtimemachine.entity.User;
import com.githubtimemachine.exception.BadRequestException;
import com.githubtimemachine.exception.ResourceNotFoundException;
import com.githubtimemachine.repository.UserRepository;
import com.githubtimemachine.security.UserPrincipal;
import com.githubtimemachine.security.jwt.JwtUtils;
import com.githubtimemachine.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final RestTemplate restTemplate;

    @Value("${github.oauth.client-id:}")
    private String clientId;

    @Value("${github.oauth.client-secret:}")
    private String clientSecret;

    @Value("${github.oauth.redirect-uri:http://localhost:8080}")
    private String redirectUri;

    @Value("${admin.usernames:admin,PriyanshuRaj2077}")
    private String adminUsernamesConfig;

    public AuthServiceImpl(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String getGitHubAuthorizationUrl() {
        return "https://github.com/login/oauth/authorize" +
                "?client_id=" + clientId +
                "&scope=read:user,user:email";
    }

    @Override
    @Transactional
    public AuthResponseDto processGitHubCallback(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new BadRequestException("Authorization code is required");
        }

        // 1. Exchange authorization code for access token
        String accessToken = exchangeCodeForAccessToken(code);

        // 2. Fetch authenticated GitHub user details
        Map<String, Object> githubUser = fetchGitHubUserProfile(accessToken);

        Long githubId = ((Number) githubUser.get("id")).longValue();
        String username = (String) githubUser.get("login");
        String displayName = (String) githubUser.get("name");
        String avatarUrl = (String) githubUser.get("avatar_url");
        String bio = (String) githubUser.get("bio");
        String email = (String) githubUser.get("email");

        if (displayName == null || displayName.isBlank()) {
            displayName = username;
        }

        // Determine Role (ROLE_ADMIN only if configured in adminUsernamesConfig, never automatically)
        List<String> adminList = Arrays.stream(adminUsernamesConfig.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        Role assignedRole = adminList.contains(username) ? Role.ROLE_ADMIN : Role.ROLE_USER;

        // 3. Find or Create User
        Optional<User> existingUserOpt = userRepository.findByGithubId(githubId);
        if (existingUserOpt.isEmpty()) {
            existingUserOpt = userRepository.findByUsername(username);
        }

        User user;
        if (existingUserOpt.isPresent()) {
            user = existingUserOpt.get();
            user.setUsername(username);
            user.setDisplayName(displayName);
            user.setAvatarUrl(avatarUrl);
            user.setBio(bio);
            if (email != null) user.setEmail(email);
            // Preserve existing admin status if present
            if (user.getRole() != Role.ROLE_ADMIN) {
                user.setRole(assignedRole);
            }
            user.setLastLogin(LocalDateTime.now());
        } else {
            user = new User(githubId, username, displayName, avatarUrl, bio, email, assignedRole);
            user.setLastLogin(LocalDateTime.now());
        }

        User savedUser = userRepository.save(user);

        // 4. Create UserPrincipal and issue JWT
        UserPrincipal principal = UserPrincipal.create(savedUser);
        String jwtToken = jwtUtils.generateToken(principal);

        AuthResponseDto.UserDto userDto = toUserDto(savedUser);
        return new AuthResponseDto(jwtToken, userDto);
    }

    @Override
    public AuthResponseDto.UserDto getCurrentUserProfile(UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            throw new BadRequestException("Unauthenticated request");
        }
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        return toUserDto(user);
    }

    private String exchangeCodeForAccessToken(String code) {
        if (clientId == null || clientId.isBlank() || clientSecret == null || clientSecret.isBlank()) {
            logger.warn("GitHub Client ID / Secret not configured in environment. Using demo authentication mode.");
            return "demo-access-token";
        }

        String tokenUrl = "https://github.com/login/oauth/access_token";

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);
            if (response.getBody() != null && response.getBody().containsKey("access_token")) {
                return (String) response.getBody().get("access_token");
            }
        } catch (Exception e) {
            logger.error("Failed to exchange code for GitHub token: {}", e.getMessage());
        }

        throw new BadRequestException("Failed to exchange authorization code with GitHub");
    }

    private Map<String, Object> fetchGitHubUserProfile(String accessToken) {
        if ("demo-access-token".equals(accessToken)) {
            // Fallback mock payload for development when client secrets are unset
            Map<String, Object> mock = new HashMap<>();
            mock.put("id", 12345678L);
            mock.put("login", "github-developer");
            mock.put("name", "GitHub Developer");
            mock.put("avatar_url", "https://avatars.githubusercontent.com/u/583231?v=4");
            mock.put("bio", "Exploring time machine history.");
            mock.put("email", "developer@github.com");
            return mock;
        }

        String userUrl = "https://api.github.com/user";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(userUrl, HttpMethod.GET, entity, Map.class);
            if (response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception e) {
            logger.error("Failed to fetch GitHub user profile: {}", e.getMessage());
        }

        throw new BadRequestException("Failed to retrieve user profile from GitHub");
    }

    private AuthResponseDto.UserDto toUserDto(User user) {
        return new AuthResponseDto.UserDto(
                user.getId(),
                user.getGithubId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getAvatarUrl(),
                user.getBio(),
                user.getEmail(),
                user.getRole(),
                user.getLastLogin()
        );
    }
}
