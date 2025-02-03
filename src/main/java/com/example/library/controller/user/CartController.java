package com.example.library.controller.user;

import com.example.library.dto.CartItem.CreateCartItemDto;
import com.example.library.dto.ResponseCartDto;
import com.example.library.dto.ResultDto;
import com.example.library.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/user/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ResultDto<ResponseCartDto>> getCart() {
        return ResponseEntity.ok(cartService.getForUser());
    }

    @PostMapping("/items")
    public ResponseEntity<ResultDto<Boolean>> addItemToCart(@RequestBody CreateCartItemDto cartItemDTO) {
        return ResponseEntity.ok(cartService.add(cartItemDTO.getBookId(), cartItemDTO.getQuantity()));

    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ResultDto<ResponseCartDto>> deleteItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.deleteItem(itemId));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ResultDto<Boolean>> delete(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.delete(cartId));
    }
}
