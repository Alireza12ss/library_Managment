package com.example.library.dto.CartItem;

import com.example.library.dto.Book.ResponseBookDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private ResponseBookDto book;
    private int quantity;
    private long totalPrice;
}
