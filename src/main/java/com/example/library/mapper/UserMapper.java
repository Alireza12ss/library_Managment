package com.example.library.mapper;

import com.example.library.dto.RegisterRequestDto;
import com.example.library.dto.UserDto;
import com.example.library.entity.Role;
import com.example.library.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    private static ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        UserMapper.modelMapper = modelMapper;
    }

    public static User mapToUser(RegisterRequestDto registerRequest, BCryptPasswordEncoder passwordEncoder) {
        User user = modelMapper.map(registerRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);
        return user;
    }

    public static UserDto mapToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}
