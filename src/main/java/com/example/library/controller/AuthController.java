package com.example.library.controller;

import com.example.library.dto.*;
import com.example.library.service.AuthService;
import com.example.library.util.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDto>> register(
            @RequestBody RegisterRequestDto requestDto
    ) {
        return ResponseEntity.ok(service.register(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(
            @RequestBody LoginRequestDto requestDto
    ) {
        return ResponseEntity.ok(service.login(requestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDto>> refreshToken(
            @RequestBody RefreshTokenRequestDto refreshTokenRequest
    ) {
        return ResponseEntity.ok(service.refresh(refreshTokenRequest));
    }
}
