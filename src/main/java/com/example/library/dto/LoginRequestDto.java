package com.example.library.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String username;
    private String password;
}
