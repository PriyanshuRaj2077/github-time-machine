package com.githubtimemachine.github.exception;

public class GitHubException extends RuntimeException {

    private final int statusCode;

    public GitHubException(String message) {
        super(message);
        this.statusCode = 500;
    }

    public GitHubException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public GitHubException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 500;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
