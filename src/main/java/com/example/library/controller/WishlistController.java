package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.repository.UserRepository;
import com.example.library.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user/wishlist")
@AllArgsConstructor
public class WishlistController{

    private final WishlistService wishlistService;

    @PostMapping("/{bookId}")
    public void addBookToWishlist(@PathVariable Long bookId) {
        wishlistService.addBookToWishlist(bookId);
    }

    @DeleteMapping("/{bookId}")
    public void removeBookFromWishlist(@PathVariable Long bookId) {
        wishlistService.removeBookFromWishlist(bookId);
    }

    @GetMapping
    public List<BookDto> getUserWishlist() {
        return wishlistService.getUserWishlist();
    }






}
