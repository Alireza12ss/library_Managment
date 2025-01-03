package com.example.library.controller;

import com.example.library.config.CustomUserDetails;
import com.example.library.dto.CartDto;
import com.example.library.dto.CartItemDto;
import com.example.library.repository.UserRepository;
import com.example.library.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    // Get Cart by userId (from token)
    @GetMapping
    public ResponseEntity<CartDto> getCartByUserId() {
        Long userId = getAuthenticatedUserId();
        CartDto cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    // Add item to cart
    @PostMapping("/items")
    public ResponseEntity<String> addItemToCart(@RequestBody CartItemDto cartItemDTO) {
        String username = getUsernameFromSecurityContext();
        cartService.addItemToCart(username, cartItemDTO.getBookId() , cartItemDTO.getQuantity());
        return ResponseEntity.ok("added succesfully!");
    }

    // Remove item from cart
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartDto> removeItemFromCart(@PathVariable Long itemId) {
        Long userId = getAuthenticatedUserId();
        CartDto updatedCart = cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok(updatedCart);
    }

    // Clear the cart
    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        Long userId = getAuthenticatedUserId();
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    private Long getAuthenticatedUserId() {
        String username = getUsernameFromSecurityContext();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"))
                .getId();
    }

    public String getUsernameFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } else if (authentication != null && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        throw new IllegalStateException("Unable to retrieve username from SecurityContext");
    }
}
