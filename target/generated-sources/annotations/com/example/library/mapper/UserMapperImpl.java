package com.example.library.mapper;

import com.example.library.dto.Auth.RegisterRequestDto;
import com.example.library.dto.Auth.ResponseUserDto;
import com.example.library.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T22:31:52+0330",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public ResponseUserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        ResponseUserDto responseUserDto = new ResponseUserDto();

        responseUserDto.setId( user.getId() );
        responseUserDto.setPassword( user.getPassword() );
        responseUserDto.setRole( user.getRole() );
        responseUserDto.setUsername( user.getUsername() );

        return responseUserDto;
    }

    @Override
    public User toEntity(RegisterRequestDto registerRequestDto) {
        if ( registerRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setPassword( registerRequestDto.getPassword() );
        user.setUsername( registerRequestDto.getUsername() );

        return user;
    }
}
