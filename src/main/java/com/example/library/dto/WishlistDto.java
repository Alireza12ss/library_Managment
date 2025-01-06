package com.example.library.dto;

import com.example.library.entity.Book;
import com.example.library.entity.User;
import lombok.Data;

@Data
public class WishlistDto {
    private String user;
    private String book;
}
