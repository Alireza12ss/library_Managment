package com.example.library.controller;

import com.example.library.dto.CartDto;
import com.example.library.dto.CartItemReqDto;
import com.example.library.util.ApiResponse;
import com.example.library.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/user/carts")
public class CartController {

    private final CartService cartService;

    // Get Cart by userId (from token)
    @GetMapping
    public ResponseEntity<ApiResponse<CartDto>> getCartByUserId() {
        CartDto cart = cartService.getCartByUserId();
        ApiResponse<CartDto> response = new ApiResponse<>("success", null, cart);
        return ResponseEntity.ok(response);
    }

    // Add item to cart
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<String>> addItemToCart(@RequestBody CartItemReqDto cartItemDTO) {
        cartService.addItemToCart(cartItemDTO.getBookId(), cartItemDTO.getQuantity());
        ApiResponse<String> response = new ApiResponse<>("success", "Item added successfully!", null);
        return ResponseEntity.ok(response);
    }

    // Remove item from cart
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartDto>> removeItemFromCart(@PathVariable Long itemId) {
        CartDto updatedCart = cartService.removeItemFromCart(itemId);
        ApiResponse<CartDto> response = new ApiResponse<>("success", null, updatedCart);
        return ResponseEntity.ok(response);
    }

    // Clear the cart
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<String>> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        ApiResponse<String> response = new ApiResponse<>("success", "Cart cleared successfully!", null);
        return ResponseEntity.ok(response);
    }
}
