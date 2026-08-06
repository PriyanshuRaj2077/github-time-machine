package com.githubtimemachine.service;

import com.githubtimemachine.dto.auth.AuthResponseDto;
import com.githubtimemachine.security.UserPrincipal;

public interface AuthService {
    String getGitHubAuthorizationUrl();
    AuthResponseDto processGitHubCallback(String code);
    AuthResponseDto.UserDto getCurrentUserProfile(UserPrincipal userPrincipal);
}
