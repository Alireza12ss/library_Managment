package com.example.library.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    public ApiResponse(T data) {
        this.status = "success";
        this.message = null;
        this.data = data;
    }

    public ApiResponse(T data, String message) {
        this.status = "success";
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }
}
