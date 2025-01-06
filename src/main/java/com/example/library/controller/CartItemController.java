package com.example.library.controller;
import com.example.library.dto.CartItemDto;
import com.example.library.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user/cart-items")
public class CartItemController {
    @Autowired
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDto> getCartItem(@PathVariable Long id) {
        CartItemDto cartItem = cartItemService.getCartItemById(id);
        return ResponseEntity.ok(cartItem);
    }
}
