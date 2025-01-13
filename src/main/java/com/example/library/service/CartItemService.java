package com.example.library.service;

import com.example.library.dto.CartItemDto;
import com.example.library.entity.CartItem;
import com.example.library.mapper.CartMapper;
import com.example.library.repository.CartItemRepository;
import com.example.library.exception.CartItemNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartItemMapper;

    public CartItemDto getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with ID: " + id));
        return cartItemMapper.mapToCartItemDto(cartItem);
    }
}
