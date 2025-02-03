package com.example.library.controller.admin;

import com.example.library.dto.ResponseCartDto;
import com.example.library.service.CartService;
import com.example.library.dto.ResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/carts")
@AllArgsConstructor
public class AdCartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ResultDto<List<ResponseCartDto>>> getList() {
        return ResponseEntity.ok(cartService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResultDto<ResponseCartDto>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getByUserId(userId));
    }
}
