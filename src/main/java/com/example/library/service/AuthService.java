package com.example.library.service;

import com.example.library.dto.*;
import com.example.library.entity.User;
import com.example.library.exception.InvalidCredentialsException;
import com.example.library.exception.UserNotFoundException;
import com.example.library.mapper.UserMapper;
import com.example.library.repository.UserRepository;
import com.example.library.util.JWTTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.library.mapper.UserMapper.mapToUser;
import static com.example.library.mapper.UserMapper.mapToUserDto;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto register(RegisterRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        User user = mapToUser(requestDto, (BCryptPasswordEncoder) passwordEncoder);
        userRepository.save(user);

        String accessToken = JWTTokenUtil.generateAccessToken(user.getUsername(), user.getRole().toString());
        String refreshToken = JWTTokenUtil.generateRefreshToken(user.getUsername());

        return new AuthResponseDto(accessToken, refreshToken);
    }

    public AuthResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String accessToken = JWTTokenUtil.generateAccessToken(user.getUsername(), user.getRole().toString());
        String refreshToken = JWTTokenUtil.generateRefreshToken(user.getUsername());

        return new AuthResponseDto(accessToken, refreshToken);
    }

    public AuthResponseDto refresh(RefreshTokenRequestDto refreshTokenRequest) {
        String accessToken = JWTTokenUtil.refreshAccessToken(refreshTokenRequest.getRefreshToken());
        return new AuthResponseDto(accessToken, refreshTokenRequest.getRefreshToken());
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        return mapToUserDto(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
