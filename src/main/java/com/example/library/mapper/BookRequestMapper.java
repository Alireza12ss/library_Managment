package com.example.library.mapper;

import com.example.library.dto.BookRequest.CreateBookRequestDto;
import com.example.library.dto.BookRequest.ResponseBookRequestDto;
import com.example.library.entity.BookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {

    ResponseBookRequestDto toDto(BookRequest bookRequest);

    BookRequest toEntity(CreateBookRequestDto createBookRequestDto);

}
