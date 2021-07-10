package com.netcracker.skillstable.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private Integer status;
    private String message;
    private T result;

    @Builder
    public ApiResponse(Integer status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
