package com.githubtimemachine.controller;

import com.githubtimemachine.dto.request.AnalysisRequestDto;
import com.githubtimemachine.dto.response.ApiResponse;
import com.githubtimemachine.dto.response.AnalyzedUserResponseDto;
import com.githubtimemachine.dto.response.RepositorySnapshotResponseDto;
import com.githubtimemachine.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    private final AnalysisService analysisService;

    // Strict Constructor Injection ONLY (No @Autowired on fields)
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse<AnalyzedUserResponseDto>> analyzeTarget(
            @Valid @RequestBody AnalysisRequestDto requestDto) {
        AnalyzedUserResponseDto result = analysisService.analyzeTarget(requestDto);
        return ResponseEntity.ok(ApiResponse.success(result, "Target analyzed successfully"));
    }

    @GetMapping("/analyze/{target}")
    public ResponseEntity<ApiResponse<AnalyzedUserResponseDto>> analyzeTargetByPath(
            @PathVariable("target") String target) {
        AnalysisRequestDto request = new AnalysisRequestDto(target);
        AnalyzedUserResponseDto result = analysisService.analyzeTarget(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=300")
                .body(ApiResponse.success(result, "Target analyzed successfully"));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<ApiResponse<AnalyzedUserResponseDto>> getProfile(
            @PathVariable("username") String username) {
        AnalyzedUserResponseDto profile = analysisService.getProfile(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=300")
                .body(ApiResponse.success(profile, "Profile retrieved successfully"));
    }

    @GetMapping("/repositories/{username}")
    public ResponseEntity<ApiResponse<List<RepositorySnapshotResponseDto>>> getRepositories(
            @PathVariable("username") String username) {
        List<RepositorySnapshotResponseDto> repos = analysisService.getRepositories(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=300")
                .body(ApiResponse.success(repos, "Repositories retrieved successfully"));
    }

    @GetMapping("/timeline/{username}")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTimeline(
            @PathVariable("username") String username) {
        List<Map<String, Object>> timeline = analysisService.getTimeline(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=300")
                .body(ApiResponse.success(timeline, "Timeline generated successfully"));
    }

    @GetMapping("/replay/{username}")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getReplay(
            @PathVariable("username") String username) {
        List<Map<String, Object>> replay = analysisService.getReplay(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=300")
                .body(ApiResponse.success(replay, "Replay events fetched successfully"));
    }

    @GetMapping("/wrapped/{username}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getWrapped(
            @PathVariable("username") String username) {
        Map<String, Object> wrapped = analysisService.getWrapped(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=300")
                .body(ApiResponse.success(wrapped, "Wrapped summary generated successfully"));
    }
}
