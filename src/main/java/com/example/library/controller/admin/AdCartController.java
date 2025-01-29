package com.example.library.controller.admin;

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
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<CartDto>> getCartByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Boolean>> clearCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.clearCartForAdmin(cartId));
    }
}
