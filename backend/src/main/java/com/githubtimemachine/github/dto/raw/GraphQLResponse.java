package com.githubtimemachine.github.dto.raw;

import java.util.List;
import java.util.Map;

public class GraphQLResponse {

    private Map<String, Object> data;
    private List<GraphQLError> errors;

    public GraphQLResponse() {
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public List<GraphQLError> getErrors() {
        return errors;
    }

    public void setErrors(List<GraphQLError> errors) {
        this.errors = errors;
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public static class GraphQLError {
        private String message;
        private List<Object> path;

        public GraphQLError() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Object> getPath() {
            return path;
        }

        public void setPath(List<Object> path) {
            this.path = path;
        }
    }
}
