package com.example.library.controller.user;

import com.example.library.dto.Book.CreateUpdateBookDto;
import com.example.library.dto.Book.ResponseBookDto;
import com.example.library.dto.ResultDto;
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
    public ResponseEntity<ResultDto<Boolean>> create(@PathVariable Long bookId) {
        return ResponseEntity.ok(wishlistService.create(bookId));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ResultDto<Boolean>> delete(@PathVariable Long bookId) {
        return ResponseEntity.ok(wishlistService.delete(bookId));
    }

    @GetMapping
    public ResponseEntity<ResultDto<List<ResponseBookDto>>> getList() {
        return ResponseEntity.ok(wishlistService.getForUser());
    }
}
