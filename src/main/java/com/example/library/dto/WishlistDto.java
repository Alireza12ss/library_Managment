package com.example.library.dto;

import com.example.library.dto.Auth.ResponseUserDto;
import com.example.library.dto.Book.ResponseBookDto;
import lombok.Data;

@Data
public class WishlistDto {
    private ResponseUserDto user;
    private ResponseBookDto book;
}
