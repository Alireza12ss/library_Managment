package com.example.library.dto.Auth;

import com.example.library.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserDto {

    private Long id;
    private String username;
    private String password;
    private Role role;
}
