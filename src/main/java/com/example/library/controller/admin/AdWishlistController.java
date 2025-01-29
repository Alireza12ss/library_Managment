package com.example.library.controller.admin;

import com.example.library.dto.BookDto;
import com.example.library.dto.WishlistDto;
import com.example.library.service.WishlistService;
import com.example.library.util.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/wishlists")
@AllArgsConstructor
public class AdWishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<WishlistDto>>> getAllWishlists() {
        return ResponseEntity.ok(wishlistService.getAllWishlists());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BookDto>>> getWishlistByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getUserWishlist(userId));
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteWishlistItem(@PathVariable Long wishlistId) {
        return ResponseEntity.ok(wishlistService.deleteWishlistItem(wishlistId));
    }
}
