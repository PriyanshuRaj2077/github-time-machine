package com.githubtimemachine.security;

import com.githubtimemachine.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class UserPrincipal implements UserDetails {

    private final UUID id;
    private final Long githubId;
    private final String username;
    private final String displayName;
    private final String avatarUrl;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UUID id, Long githubId, String username, String displayName, String avatarUrl, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.githubId = githubId;
        this.username = username;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
        return new UserPrincipal(
                user.getId(),
                user.getGithubId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getAvatarUrl(),
                user.getEmail(),
                Collections.singletonList(authority)
        );
    }

    public UUID getId() {
        return id;
    }

    public Long getGithubId() {
        return githubId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
