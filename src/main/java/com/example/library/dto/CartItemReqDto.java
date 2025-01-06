package com.example.library.dto;

import lombok.Getter;

@Getter
public class CartItemReqDto {
    private Long bookId;
    private int quantity;
}
