package com.example.library.service;

import java.util.List;

import com.example.library.dto.Auth.*;
import com.example.library.entity.Role;
import com.example.library.entity.User;
import com.example.library.mapper.UserMapper;
import com.example.library.repository.UserRepository;
import com.example.library.dto.ResultDto;
import com.example.library.util.JWTTokenUtil;
import com.example.library.util.ResponseUtil;
import com.raika.customexception.exceptions.BaseException;
import com.raika.customexception.exceptions.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public ResultDto<AuthResponseDto> register(RegisterRequestDto requestDto) {
        try {
            if (userRepository.existsByUsername(requestDto.getUsername())) {
                throw new CustomException.Conflict("Username already taken");
            }
            User user = userMapper.toEntity(requestDto);
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            user.setRole(Role.USER);
            userRepository.save(user);
            String accessToken = JWTTokenUtil.generateAccessToken(user.getUsername(), String.valueOf(user.getRole()));
            String refreshToken = JWTTokenUtil.generateRefreshToken(user.getUsername());
            return ResponseUtil.created(new AuthResponseDto(accessToken, refreshToken));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<AuthResponseDto> login(LoginRequestDto requestDto) {
        try {
            String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
            User user = userRepository.findUserByUsernameAndPassword(requestDto.getUsername(), encodedPassword)
                    .orElseThrow(() -> new CustomException.ValidationFailure("Invalid username or password"));
            String accessToken = JWTTokenUtil.generateAccessToken(user.getUsername(), String.valueOf(user.getRole()));
            String refreshToken = JWTTokenUtil.generateRefreshToken(user.getUsername());
            return ResponseUtil.success(new AuthResponseDto(accessToken, refreshToken));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<AuthResponseDto> refreshToken(RefreshTokenRequestDto refreshTokenRequest) {
        try {
            String accessToken = JWTTokenUtil.refreshAccessToken(refreshTokenRequest.getRefreshToken());
            return ResponseUtil.success(new AuthResponseDto(accessToken, refreshTokenRequest.getRefreshToken()));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<List<ResponseUserDto>> getList() {
        try {
            var list = userRepository.findAll()
                    .stream()
                    .map(userMapper::toDto)
                    .toList();
            return ResponseUtil.success(list);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<ResponseUserDto> getUserById(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new CustomException.NotFound("User not found with ID: " + id));
            return ResponseUtil.success(userMapper.toDto(user));
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }

    public ResultDto<Boolean> delete(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new CustomException.NotFound("User not found with ID: " + id);
            }
            userRepository.deleteById(id);
            return ResponseUtil.success(true);
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CustomException.ServerError(exception.getMessage());
        }
    }
}
