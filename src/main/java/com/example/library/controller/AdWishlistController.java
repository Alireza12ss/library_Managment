package com.example.library.controller;

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
        List<WishlistDto> wishlists = wishlistService.getAllWishlists();
        ApiResponse<List<WishlistDto>> response = new ApiResponse<>("success", null, wishlists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BookDto>>> getWishlistByUser(@PathVariable Long userId) {
        List<BookDto> wishlist = wishlistService.getUserWishlist(userId);
        ApiResponse<List<BookDto>> response = new ApiResponse<>("success", null, wishlist);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<ApiResponse<Void>> deleteWishlistItem(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlistItem(wishlistId);
        ApiResponse<Void> response = new ApiResponse<>("success", "Wishlist item deleted successfully", null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
