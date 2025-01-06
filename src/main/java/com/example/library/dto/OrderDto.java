package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long userId;
    private List<CartItemDto> cartItems; // List of items in the order
    private Date orderDate; // Date the order was placed
    private long totalPrice; // Total price of the order
    private boolean paymentCompleted;
}
