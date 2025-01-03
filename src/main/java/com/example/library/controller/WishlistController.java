package com.example.library.controller;

import com.example.library.config.CustomUserDetails;
import com.example.library.dto.BookDto;
import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import com.example.library.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserRepository userRepository;

    @PostMapping("/{bookId}")
    public void addBookToWishlist(@PathVariable Long bookId) {
        Long userId = getCurrentUserId();
        wishlistService.addBookToWishlist(userId, bookId);
    }

    @DeleteMapping("/{bookId}")
    public void removeBookFromWishlist(@PathVariable Long bookId) {
        Long userId = getCurrentUserId();
        wishlistService.removeBookFromWishlist(userId, bookId);
    }

    @GetMapping
    public List<BookDto> getUserWishlist() {
        Long userId = getCurrentUserId();
        return wishlistService.getUserWishlist(userId);
    }

    private Long getCurrentUserId() {
        String username = getUsernameFromSecurityContext();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"))
                .getId();
    }

    public String getUsernameFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } else if (authentication != null && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        throw new IllegalStateException("Unable to retrieve username from SecurityContext");
    }


}
