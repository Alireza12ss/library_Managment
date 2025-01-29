package com.example.library.controller.user;

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
        return ResponseEntity.ok(cartService.getCartByUserId());
    }

    // Add item to cart
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<Boolean>> addItemToCart(@RequestBody CartItemReqDto cartItemDTO) {
        return ResponseEntity.ok(cartService.addItemToCart(cartItemDTO.getBookId(), cartItemDTO.getQuantity()));

    }

    // Remove item from cart
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartDto>> removeItemFromCart(@PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(itemId));
    }

    // Clear the cart
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Boolean>> clearCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.clearCart(cartId));
    }
}
