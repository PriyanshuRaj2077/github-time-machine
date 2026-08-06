package com.githubtimemachine.service;

import com.githubtimemachine.dto.request.AnalysisRequestDto;
import com.githubtimemachine.dto.response.AnalyticsSnapshotResponseDto;
import com.githubtimemachine.dto.response.AnalyzedUserResponseDto;
import com.githubtimemachine.dto.response.RepositorySnapshotResponseDto;

import java.util.List;
import java.util.Map;

public interface AnalysisService {

    AnalyzedUserResponseDto analyzeTarget(AnalysisRequestDto requestDto);

    AnalyzedUserResponseDto getProfile(String username);

    List<RepositorySnapshotResponseDto> getRepositories(String username);

    List<Map<String, Object>> getTimeline(String username);

    List<Map<String, Object>> getReplay(String username);

    Map<String, Object> getWrapped(String username);

    AnalyticsSnapshotResponseDto getInsights(String username);
}
