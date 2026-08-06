package com.githubtimemachine.controller;

import com.githubtimemachine.dto.auth.AuthResponseDto;
import com.githubtimemachine.dto.auth.OAuthCodeRequestDto;
import com.githubtimemachine.dto.response.ApiResponse;
import com.githubtimemachine.security.UserPrincipal;
import com.githubtimemachine.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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
    public ResponseEntity<ApiResponse<AuthResponseDto>> handleGitHubCallback(
            @RequestParam(value = "code", required = false) String queryCode,
            @RequestBody(required = false) OAuthCodeRequestDto request) {
        String code = (request != null && request.getCode() != null && !request.getCode().isBlank())
                ? request.getCode()
                : queryCode;

        if (code == null || code.isBlank()) {
            throw new com.githubtimemachine.exception.BadRequestException("OAuth authorization code is required");
        }

        logger.info("[AuthController] Received POST OAuth callback with code length: {}", code.length());
        AuthResponseDto response = authService.processGitHubCallback(code);
        logger.info("[AuthController] OAuth authentication successful for user: {}", response.getUser() != null ? response.getUser().getUsername() : "unknown");
        return ResponseEntity.ok(ApiResponse.success(response, "Authentication successful"));
    }

    @GetMapping("/github/callback")
    public ResponseEntity<ApiResponse<AuthResponseDto>> handleGitHubCallbackGet(@RequestParam("code") String code) {
        logger.info("[AuthController] Received GET OAuth callback with code length: {}", code != null ? code.length() : 0);
        AuthResponseDto response = authService.processGitHubCallback(code);
        logger.info("[AuthController] GET OAuth authentication successful for user: {}", response.getUser() != null ? response.getUser().getUsername() : "unknown");
        return ResponseEntity.ok(ApiResponse.success(response, "Authentication successful"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthResponseDto.UserDto>> getCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        AuthResponseDto.UserDto user = authService.getCurrentUserProfile(principal);
        return ResponseEntity.ok(ApiResponse.success(user));
    }
}
