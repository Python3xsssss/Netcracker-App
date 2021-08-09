package com.netcracker.skillstable.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiResponse<T> {
    private int httpStatusCode;
    private HttpStatus status;
    private String message;
    private T result;

    @Builder
    public ApiResponse(HttpStatus status, String message, T result) {
        this.httpStatusCode = status.value();
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
