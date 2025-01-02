package com.example.library.dto;

import com.example.library.entity.WishList;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String role;
    private List<WishList> wishLists = new ArrayList<>();
}
