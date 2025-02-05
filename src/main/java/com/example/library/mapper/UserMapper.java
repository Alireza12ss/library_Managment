package com.example.library.mapper;

import com.example.library.dto.Auth.RegisterRequestDto;
import com.example.library.dto.Auth.ResponseUserDto;
import com.example.library.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ResponseUserDto toDto(User user);
    User toEntity(RegisterRequestDto registerRequestDto);
}

