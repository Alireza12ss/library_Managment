package com.example.library.dto;

import com.example.library.dto.Auth.ResponseUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderDto {
    private Long id;
    private ResponseUserDto user;
//    private List<CartItemDto> cartItems;
    private Date orderDate;
    private long totalPrice;
    private boolean paymentCompleted;
}
