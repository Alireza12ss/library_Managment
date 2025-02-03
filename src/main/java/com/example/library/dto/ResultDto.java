package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto<T> {
    private boolean isSuccess;
    private int statusCode;
    private String message;
    private T data;
}
