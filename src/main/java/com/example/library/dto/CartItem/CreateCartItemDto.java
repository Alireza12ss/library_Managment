package com.example.library.dto.CartItem;

import lombok.Getter;

@Getter
public class CreateCartItemDto {
    private Long bookId;
    private int quantity;
}
