package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.util.ApiResponse;
import com.example.library.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user/wishlist")
@AllArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> addBookToWishlist(@PathVariable Long bookId) {
        wishlistService.addBookToWishlist(bookId);
        ApiResponse<Void> response = new ApiResponse<>("success", "Book added to wishlist", null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> removeBookFromWishlist(@PathVariable Long bookId) {
        wishlistService.removeBookFromWishlist(bookId);
        ApiResponse<Void> response = new ApiResponse<>("success", "Book removed from wishlist", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDto>>> getUserWishlist() {
        List<BookDto> wishlist = wishlistService.getUserWishlist();
        ApiResponse<List<BookDto>> response = new ApiResponse<>("success", null, wishlist);
        return ResponseEntity.ok(response);
    }
}
