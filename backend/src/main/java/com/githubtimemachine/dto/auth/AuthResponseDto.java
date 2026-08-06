package com.githubtimemachine.dto.auth;

import com.githubtimemachine.entity.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthResponseDto {

    private String token;
    private String tokenType = "Bearer";
    private UserDto user;

    public AuthResponseDto() {
    }

    public AuthResponseDto(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public static class UserDto {
        private UUID id;
        private Long githubId;
        private String username;
        private String displayName;
        private String avatarUrl;
        private String bio;
        private String email;
        private Role role;
        private LocalDateTime lastLogin;

        public UserDto() {
        }

        public UserDto(UUID id, Long githubId, String username, String displayName, String avatarUrl, String bio, String email, Role role, LocalDateTime lastLogin) {
            this.id = id;
            this.githubId = githubId;
            this.username = username;
            this.displayName = displayName;
            this.avatarUrl = avatarUrl;
            this.bio = bio;
            this.email = email;
            this.role = role;
            this.lastLogin = lastLogin;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public Long getGithubId() {
            return githubId;
        }

        public void setGithubId(Long githubId) {
            this.githubId = githubId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public LocalDateTime getLastLogin() {
            return lastLogin;
        }

        public void setLastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
        }
    }
}
