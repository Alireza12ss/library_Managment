package com.example.library.dto;

import com.example.library.dto.Auth.ResponseUserDto;
import com.example.library.dto.CartItem.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCartDto {
    private ResponseUserDto user;
    private List<CartItemDto> cartItems;
    private long totalPrice;
}
