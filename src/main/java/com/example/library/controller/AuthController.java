package com.example.library.controller;
import com.example.library.dto.*;
import com.example.library.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AuthController {

    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @RequestBody RegisterRequestDto requestDto
            ){
        return ResponseEntity.ok(service.register(requestDto));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody LoginRequestDto requestDto
    ){
        return ResponseEntity.ok(service.login(requestDto));

    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refreshToken(
            @RequestBody RefreshTokenRequestDto refreshTokenRequest
    ) {
        return ResponseEntity.ok(service.refresh(refreshTokenRequest));
    }





}
