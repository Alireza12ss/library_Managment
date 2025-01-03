package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDto {
    private Long bookId;
    private String bookTitle;
    private int quantity;
    private long pricePerUnit;
    private long totalPrice;
}
