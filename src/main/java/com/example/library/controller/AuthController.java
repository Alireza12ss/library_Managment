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
        AuthResponseDto authResponse = service.register(requestDto);
        ApiResponse<AuthResponseDto> response = new ApiResponse<>("success", "Registration successful", authResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(
            @RequestBody LoginRequestDto requestDto
    ) {
        AuthResponseDto authResponse = service.login(requestDto);
        ApiResponse<AuthResponseDto> response = new ApiResponse<>("success", "Login successful", authResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDto>> refreshToken(
            @RequestBody RefreshTokenRequestDto refreshTokenRequest
    ) {
        AuthResponseDto authResponse = service.refresh(refreshTokenRequest);
        ApiResponse<AuthResponseDto> response = new ApiResponse<>("success", "Token refreshed successfully", authResponse);
        return ResponseEntity.ok(response);
    }
}
