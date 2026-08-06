package com.githubtimemachine.controller;

import com.githubtimemachine.dto.admin.AdminDashboardDto;
import com.githubtimemachine.dto.response.ApiResponse;
import com.githubtimemachine.entity.User;
import com.githubtimemachine.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<AdminDashboardDto>> getDashboardStats() {
        AdminDashboardDto stats = adminService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats, "Admin dashboard statistics retrieved"));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users, "User list retrieved"));
    }

    @GetMapping("/analytics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAnalyticsSummary() {
        Map<String, Object> summary = adminService.getAnalyticsSummary();
        return ResponseEntity.ok(ApiResponse.success(summary, "Analytics summary retrieved"));
    }

    @GetMapping("/system")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSystemStatus() {
        Map<String, Object> status = adminService.getSystemStatus();
        return ResponseEntity.ok(ApiResponse.success(status, "System status retrieved"));
    }
}
