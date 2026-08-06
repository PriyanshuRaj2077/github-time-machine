package com.githubtimemachine.controller;

import com.githubtimemachine.dto.response.ApiResponse;
import com.githubtimemachine.entity.AnalysisHistory;
import com.githubtimemachine.repository.AnalysisHistoryRepository;
import com.githubtimemachine.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserHistoryController {

    private final AnalysisHistoryRepository analysisHistoryRepository;

    public UserHistoryController(AnalysisHistoryRepository analysisHistoryRepository) {
        this.analysisHistoryRepository = analysisHistoryRepository;
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<AnalysisHistory>>> getMyHistory(@AuthenticationPrincipal UserPrincipal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(ApiResponse.error("Unauthorized"));
        }
        List<AnalysisHistory> historyList = analysisHistoryRepository.findByUserIdOrderByCreatedAtDesc(principal.getId());
        return ResponseEntity.ok(ApiResponse.success(historyList));
    }
}
