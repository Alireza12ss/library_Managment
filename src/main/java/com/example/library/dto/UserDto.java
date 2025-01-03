package com.example.library.dto;

import com.example.library.entity.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String role;
    private List<Wishlist> wishLists = new ArrayList<>();
}
