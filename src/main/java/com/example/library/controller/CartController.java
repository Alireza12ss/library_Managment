package com.example.library.controller;

import com.example.library.dto.CartDto;
import com.example.library.dto.CartItemReqDto;
import com.example.library.repository.UserRepository;
import com.example.library.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/user/carts")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    // Get Cart by userId (from token)
    @GetMapping
    public ResponseEntity<CartDto> getCartByUserId() {
        CartDto cart = cartService.getCartByUserId();
        return ResponseEntity.ok(cart);
    }

    // Add item to cart
    @PostMapping("/items")
    public ResponseEntity<String> addItemToCart(@RequestBody CartItemReqDto cartItemDTO) {
        cartService.addItemToCart(cartItemDTO.getBookId(), cartItemDTO.getQuantity());
        return ResponseEntity.ok("added succesfully!");
    }

    // Remove item from cart
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartDto> removeItemFromCart(@PathVariable Long itemId) {
        CartDto updatedCart = cartService.removeItemFromCart(itemId);
        return ResponseEntity.ok(updatedCart);
    }

    // Clear the cart
    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }


}
