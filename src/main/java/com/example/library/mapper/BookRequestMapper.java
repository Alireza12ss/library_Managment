package com.example.library.mapper;

import com.example.library.dto.BookRequestDto;
import com.example.library.entity.BookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {

    BookRequestDto toDto(BookRequest bookRequest);

    BookRequest toEntity(BookRequestDto bookRequestDto);

    void partialUpdate(BookRequestDto bookRequestDto, @MappingTarget BookRequest bookRequest);
}
