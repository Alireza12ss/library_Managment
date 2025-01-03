package com.example.library.service;
import com.example.library.dto.CartItemDto;
import com.example.library.entity.CartItem;
import com.example.library.repository.CartItemRepository;
import com.example.library.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemDto getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        return mapToCartItemDTO(cartItem);
    }

    private CartItemDto mapToCartItemDTO(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getBook().getId(),
                cartItem.getBook().getTitle(),
                cartItem.getQuantity(),
                cartItem.getBook().getPrice(),
                cartItem.getBook().getPrice() * cartItem.getQuantity()
        );
    }
}
