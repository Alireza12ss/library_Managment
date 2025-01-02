package com.example.library.service;

import com.example.library.dto.AuthResponseDto;
import com.example.library.dto.LoginRequestDto;
import com.example.library.dto.RefreshTokenRequestDto;
import com.example.library.dto.RegisterRequestDto;
import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import com.example.library.util.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user
    public AuthResponseDto register(RegisterRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())){
            throw new RuntimeException("Username already taken");
        }
        User user = mapToUser(requestDto , (BCryptPasswordEncoder) passwordEncoder);
        userRepository.save(user);

        String accessToken = JWTTokenUtil.generateAccessToken(user.getUsername());
        String refreshToken = JWTTokenUtil.generateRefreshToken(user.getUsername());

        return new AuthResponseDto(accessToken,refreshToken);
    }


    public User mapToUser(RegisterRequestDto registerRequest, BCryptPasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Encrypt password
        user.setRole(registerRequest.getRole());
        return user;
    }


    public AuthResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new RuntimeException(("Invalid username or password")));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String accessToken = JWTTokenUtil.generateAccessToken(user.getUsername());
        String refreshToken = JWTTokenUtil.generateRefreshToken(user.getUsername());

        return new AuthResponseDto(accessToken,refreshToken);
    }

    public AuthResponseDto refresh(RefreshTokenRequestDto refreshTokenRequest) {
        String accessToken = JWTTokenUtil.refreshAccessToken(refreshTokenRequest.getRefreshToken());
        return new AuthResponseDto(accessToken , refreshTokenRequest.getRefreshToken());
    }
}
