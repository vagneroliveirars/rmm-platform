package com.ninjaone.rmmplatform.controller.dto;

import java.time.ZonedDateTime;

public class ErrorResponseDTO {

    private ZonedDateTime timestamp;
    private String path;
    private int status;
    private String error;
    private String message;

    public ErrorResponseDTO(ZonedDateTime timestamp, String path, int status, String error, String message) {
        this.timestamp = timestamp;
        this.path = path;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
