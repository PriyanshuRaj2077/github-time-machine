package com.githubtimemachine.constants;

public final class AppConstants {

    private AppConstants() {
        // Prevent instantiation
    }

    public static final String API_BASE_PATH = "/api";
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "createdAt";
    public static final String DEFAULT_SORT_DIRECTION = "desc";

    public static final String HEADER_TIMEZONE = "X-Timezone";
    public static final String SYSTEM_USER = "SYSTEM";
    
    public static final String MSG_SUCCESS = "Operation completed successfully";
    public static final String MSG_USER_NOT_FOUND = "User analysis not found with username: ";
    public static final String MSG_REPO_NOT_FOUND = "Repository snapshot not found with ID: ";
}
