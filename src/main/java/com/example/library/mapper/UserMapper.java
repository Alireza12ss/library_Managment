package com.example.library.mapper;

import com.example.library.dto.RegisterRequestDto;
import com.example.library.dto.UserDto;
import com.example.library.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role", target = "role")
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true) // Ensure ID is not set during creation
    User toEntity(RegisterRequestDto registerRequestDto);

    void partialUpdate(RegisterRequestDto registerRequestDto, @MappingTarget User user);
}

