package com.githubtimemachine.controller;

import com.githubtimemachine.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> checkHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "github-time-machine-backend");
        health.put("runtime", "Java 21");
        health.put("framework", "Spring Boot 3.2.5");

        return ResponseEntity.ok(ApiResponse.success("System operational", health));
    }
}
