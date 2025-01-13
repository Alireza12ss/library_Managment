package com.example.library.controller;

import com.example.library.dto.CartDto;
import com.example.library.service.CartService;
import com.example.library.util.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/carts")
@AllArgsConstructor
public class AdCartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartDto>>> getAllCarts() {
        List<CartDto> carts = cartService.getAllCarts();
        ApiResponse<List<CartDto>> response = new ApiResponse<>("success", null, carts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<CartDto>> getCartByUser(@PathVariable Long userId) {
        CartDto cart = cartService.getCartByUserId(userId);
        ApiResponse<CartDto> response = new ApiResponse<>("success", null, cart);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Void>> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        ApiResponse<Void> response = new ApiResponse<>("success", "Cart cleared successfully", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
