package com.example.library.mapper;

import com.example.library.dto.CartDto;
import com.example.library.dto.CartItemDto;
import com.example.library.entity.Cart;
import com.example.library.entity.CartItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public static CartDto mapToCartDto(Cart cart) {
        return new CartDto(
                cart.getUser().getId(),
                cart.getCartItems().stream()
                        .map(CartMapper::mapToCartItemDto)
                        .collect(Collectors.toList()),
                cart.getCartItems().stream()
                        .mapToLong(item -> item.getBook().getPrice() * item.getQuantity())
                        .sum()
        );
    }

    public static CartItemDto mapToCartItemDto(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getBook().getId(),
                cartItem.getBook().getTitle(),
                cartItem.getQuantity(),
                cartItem.getBook().getPrice(),
                cartItem.getBook().getPrice() * cartItem.getQuantity()
        );
    }
}
