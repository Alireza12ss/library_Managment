package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    private Long userId; // User ID associated with the cart
    private List<CartItemDto> cartItems; // List of items in the cart
    private long totalPrice;
}
