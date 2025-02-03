package com.example.library.controller.admin;

import com.example.library.dto.Book.CreateUpdateBookDto;
import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.dto.WishlistDto;
import com.example.library.service.WishlistService;
import com.example.library.dto.ResultDto;
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
    public ResponseEntity<ResultDto<List<WishlistDto>>> getList() {
        return ResponseEntity.ok(wishlistService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResultDto<List<ResponseBookDto>>> getWishlistByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getForAdmin(userId));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ResultDto<Boolean>> delete(@PathVariable Long bookId) {
        return ResponseEntity.ok(wishlistService.delete(bookId));
    }
}
