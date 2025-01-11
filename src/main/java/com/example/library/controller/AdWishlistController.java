package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.dto.WishlistDto;
import com.example.library.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/v1/admin/wishlists")
@AllArgsConstructor
public class AdWishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public List<WishlistDto> getAllWishlists() {
        return wishlistService.getAllWishLists();
    }

    @GetMapping("/user/{userId}")
    public List<BookDto> getWishlistByUser(@PathVariable Long userId) {
        return wishlistService.getUserWishlist(userId);
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlistItem(wishlistId);
        return ResponseEntity.noContent().build();
    }
}
