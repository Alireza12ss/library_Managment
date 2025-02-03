package com.example.library.controller;

import com.example.library.dto.Auth.AuthResponseDto;
import com.example.library.dto.Auth.LoginRequestDto;
import com.example.library.dto.Auth.RefreshTokenRequestDto;
import com.example.library.dto.Auth.RegisterRequestDto;
import com.example.library.service.AuthService;
import com.example.library.dto.ResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ResultDto<AuthResponseDto>> register(
            @RequestBody RegisterRequestDto requestDto
    ) {
        return ResponseEntity.ok(service.register(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ResultDto<AuthResponseDto>> login(
            @RequestBody LoginRequestDto requestDto
    ) {
        return ResponseEntity.ok(service.login(requestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResultDto<AuthResponseDto>> refreshToken(
            @RequestBody RefreshTokenRequestDto refreshTokenRequest
    ) {
        return ResponseEntity.ok(service.refreshToken(refreshTokenRequest));
    }
}
