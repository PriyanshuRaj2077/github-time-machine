package com.githubtimemachine.service;

import com.githubtimemachine.dto.admin.AdminDashboardDto;
import com.githubtimemachine.entity.User;

import java.util.List;
import java.util.Map;

public interface AdminService {
    AdminDashboardDto getDashboardStats();
    List<User> getAllUsers();
    Map<String, Object> getAnalyticsSummary();
    Map<String, Object> getSystemStatus();
}
