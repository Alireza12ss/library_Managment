package com.example.library.service;

import com.example.library.dto.*;
import com.example.library.entity.Cart;
import com.example.library.entity.User;
import com.example.library.repository.CartRepository;
import com.example.library.repository.UserRepository;
import com.example.library.util.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user
    public AuthResponseDto register(RegisterRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())){
            throw new RuntimeException("Username already taken");
        }
        if (!requestDto.getRole().equals("USER")){
            throw new RuntimeException("Access Denied");
        }
        User user = mapToUser(requestDto, (BCryptPasswordEncoder) passwordEncoder);
        userRepository.save(user);
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        String accessToken = JWTTokenUtil.generateAccessToken(user.getUsername());
        String refreshToken = JWTTokenUtil.generateRefreshToken(user.getUsername());

        return new AuthResponseDto(accessToken, refreshToken);
    }

    // Map RegisterRequestDto to User entity
    public User mapToUser(RegisterRequestDto registerRequest, BCryptPasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Encrypt password
        user.setRole(registerRequest.getRole());
        return user;
    }

    // Login user
    public AuthResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String accessToken = JWTTokenUtil.generateAccessToken(user.getUsername());
        String refreshToken = JWTTokenUtil.generateRefreshToken(user.getUsername());

        return new AuthResponseDto(accessToken, refreshToken);
    }

    // Refresh access token using refresh token
    public AuthResponseDto refresh(RefreshTokenRequestDto refreshTokenRequest) {
        String accessToken = JWTTokenUtil.refreshAccessToken(refreshTokenRequest.getRefreshToken());
        return new AuthResponseDto(accessToken, refreshTokenRequest.getRefreshToken());
    }

    // Get all users (Admin functionality)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    // Get user by ID
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserDto(user);
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // Helper method to map User entity to UserDto
    private UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}
