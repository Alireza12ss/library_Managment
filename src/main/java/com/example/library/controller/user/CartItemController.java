package com.example.library.controller.user;

import com.example.library.dto.CartItemDto;
import com.example.library.util.ApiResponse;
import com.example.library.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/user/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CartItemDto>> getCartItem(@PathVariable Long id) {
        return ResponseEntity.ok(cartItemService.getCartItemById(id));
    }
}
