package com.example.library.controller.user;

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
    public ResponseEntity<ApiResponse<Boolean>> addBookToWishlist(@PathVariable Long bookId) {
        return ResponseEntity.ok(wishlistService.addBookToWishlist(bookId));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Boolean>> removeBookFromWishlist(@PathVariable Long bookId) {
        return ResponseEntity.ok(wishlistService.removeBookFromWishlist(bookId));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDto>>> getUserWishlist() {
        return ResponseEntity.ok(wishlistService.getUserWishlist());
    }
}
