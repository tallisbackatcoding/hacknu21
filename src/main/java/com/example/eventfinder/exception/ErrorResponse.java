package com.example.eventfinder.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private String error;
    private String path;

    public static class Builder {

        private String message;
        private String error;
        private String path;

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setError(String error) {
            this.error = error;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(message, error, path);
        }
    }
}
