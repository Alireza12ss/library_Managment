package com.example.library.mapper;

import com.example.library.dto.Auth.RegisterRequestDto;
import com.example.library.dto.Auth.ResponseUserDto;
import com.example.library.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-05T14:47:19+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
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
        responseUserDto.setUsername( user.getUsername() );
        responseUserDto.setPassword( user.getPassword() );
        responseUserDto.setRole( user.getRole() );

        return responseUserDto;
    }

    @Override
    public User toEntity(RegisterRequestDto registerRequestDto) {
        if ( registerRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( registerRequestDto.getUsername() );
        user.setPassword( registerRequestDto.getPassword() );

        return user;
    }
}
