package com.example.library.dto;

import com.example.library.dto.CartItem.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCartDto {
    private Long userId; // User ID associated with the cart
    private List<CartItemDto> cartItems; // List of items in the cart
    private long totalPrice;
}
