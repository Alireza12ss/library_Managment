package com.example.library.dto;

import com.example.library.entity.Role;
import com.example.library.entity.Wishlist;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private Role role;
    private List<Wishlist> wishLists = new ArrayList<>();
}
