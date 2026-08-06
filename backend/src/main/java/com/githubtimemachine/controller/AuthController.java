package com.githubtimemachine.controller;

import com.githubtimemachine.dto.auth.AuthResponseDto;
import com.githubtimemachine.dto.auth.OAuthCodeRequestDto;
import com.githubtimemachine.dto.response.ApiResponse;
import com.githubtimemachine.security.UserPrincipal;
import com.githubtimemachine.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/github/url")
    public ResponseEntity<ApiResponse<Map<String, String>>> getGitHubAuthUrl() {
        String url = authService.getGitHubAuthorizationUrl();
        return ResponseEntity.ok(ApiResponse.success(Map.of("url", url)));
    }

    @PostMapping("/github/callback")
    public ResponseEntity<ApiResponse<AuthResponseDto>> handleGitHubCallback(@Valid @RequestBody OAuthCodeRequestDto request) {
        AuthResponseDto response = authService.processGitHubCallback(request.getCode());
        return ResponseEntity.ok(ApiResponse.success(response, "Authentication successful"));
    }

    @GetMapping("/github/callback")
    public ResponseEntity<ApiResponse<AuthResponseDto>> handleGitHubCallbackGet(@RequestParam("code") String code) {
        AuthResponseDto response = authService.processGitHubCallback(code);
        return ResponseEntity.ok(ApiResponse.success(response, "Authentication successful"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthResponseDto.UserDto>> getCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        AuthResponseDto.UserDto user = authService.getCurrentUserProfile(principal);
        return ResponseEntity.ok(ApiResponse.success(user));
    }
}
