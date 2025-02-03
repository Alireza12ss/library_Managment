package com.example.library.dto;

import com.example.library.dto.CartItem.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrderDto {
    private Long orderId;
    private Long userId;
    private List<CartItemDto> cartItems;
    private Date orderDate;
    private long totalPrice;
    private boolean paymentCompleted;
}
