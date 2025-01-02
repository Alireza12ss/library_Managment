package com.example.library.dto;

import com.example.library.entity.WishList;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RegisterRequestDto {
    private String username;
    private String password;
    private String role;
}
